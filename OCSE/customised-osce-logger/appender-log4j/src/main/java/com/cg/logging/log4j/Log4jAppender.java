package com.cg.logging.log4j;

import static com.cg.logging.aws.AwsClientHelpers.buildClient;

import java.util.UUID;

import org.apache.log4j.Appender;
import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.spi.Filter;
import org.apache.log4j.spi.LoggingEvent;
import org.apache.log4j.spi.OptionHandler;

import com.amazonaws.services.s3.AmazonS3Client;
import com.cg.logging.BufferPublisher;
import com.cg.logging.CapacityBasedBufferMonitor;
import com.cg.logging.Event;
import com.cg.logging.IBufferMonitor;
import com.cg.logging.IBufferPublisher;
import com.cg.logging.LoggingEventCache;
import com.cg.logging.TimePeriodBasedBufferMonitor;
import com.cg.logging.aws.S3Configuration;
import com.cg.logging.aws.S3PublishHelper;

public class Log4jAppender extends AppenderSkeleton implements Appender, OptionHandler {

    static final int DEFAULT_THRESHOLD = 2000;

    private int stagingBufferSize = DEFAULT_THRESHOLD;
    private int stagingBufferAge = 0;

    private LoggingEventCache<Event> stagingLog = null;

    private volatile String[] tags;
    private volatile String hostName;

    private S3Configuration s3;

    private AmazonS3Client s3Client;
    private boolean s3Compression = false;

    private boolean verbose = false;

    @Override
    public void close() {
        if (verbose) {
            System.out.println("close(): Cleaning up resources");
        }
        if (null != stagingLog) {
            stagingLog.flushAndPublish();
            stagingLog = null;
        }
    }

    @Override
    public boolean requiresLayout() {
        return true;
    }

    public void setStagingBufferSize(int buffer) {
        stagingBufferSize = buffer;
    }

    public void setStagingBufferAge(int minutes) {
        stagingBufferAge = minutes;
    }

    // general properties
    ///////////////////////////////////////////////////////////////////////////
    public void setVerbose(String verboseStr) {
        this.verbose = Boolean.parseBoolean(verboseStr);
    }

    // S3 properties
    ///////////////////////////////////////////////////////////////////////////
    private S3Configuration getS3() {
        if (null == s3) {
            s3 = new S3Configuration();
        }
        return s3;
    }

    public void setS3Bucket(String bucket) {
        getS3().setBucket(bucket);
    }

    public void setS3Region(String region) {
        getS3().setRegion(region);
    }

    public void setS3Path(String path) {
        getS3().setPath(path);
    }

    public void setS3AwsKey(String accessKey) {
        getS3().setAccessKey(accessKey);
    }

    public void setS3AwsSecret(String secretKey) {
        getS3().setSecretKey(secretKey);
    }

    public void setS3ServiceEndpoint(String serviceEndpoint) {
        getS3().setServiceEndpoint(serviceEndpoint);
    }

    public void setS3SigningRegion(String signingRegion) {
        getS3().setSigningRegion(signingRegion);
    }

    public void setS3Compression(String enable) {
        s3Compression = Boolean.parseBoolean(enable);
    }

    public void setTags(String tags) {
        if (null != tags) {
            this.tags = tags.split("[,;]");
            for (int i = 0; i < this.tags.length; i++) {
                this.tags[i] = this.tags[i].trim();
            }
        }
    }

    @Override
    protected void append(LoggingEvent evt) {
        try {
            stagingLog.add(mapToEvent(evt));
        } catch (Exception ex) {
            errorHandler.error("Cannot append event", ex, 105, evt);
        }
    }

    @Override
    public void activateOptions() {
        super.activateOptions();
        try {
            initFilters();
            java.net.InetAddress addr = java.net.InetAddress.getLocalHost();
            hostName = addr.getHostName();
            if (null != s3) {
                s3Client = (AmazonS3Client) buildClient(s3.getAccessKey(), s3.getSecretKey(), s3
                        .getRegion(), s3.getServiceEndpoint(), s3.getSigningRegion());
            }
            initStagingLog();
        } catch (Exception ex) {
            errorHandler.error("Cannot initialize resources", ex, 100);
        }
    }

    void initFilters() {
        addFilter(new Filter() {
            @Override
            public int decide(LoggingEvent event) {
                // To prevent infinite looping, we filter out events from
                // the publishing thread
                int decision = Filter.NEUTRAL;
                if (LoggingEventCache.PUBLISH_THREAD_NAME.equals(event.getThreadName())) {
                    decision = Filter.DENY;
                }
                return decision;
            }
        });
    }

    void initStagingLog() throws Exception {
        if (null == stagingLog) {
            UUID uuid = UUID.randomUUID();
            stagingLog = new LoggingEventCache<Event>(uuid.toString().replaceAll("-", ""),
                    createCacheMonitor(), createCachePublisher());

            Runtime.getRuntime().addShutdownHook(new Thread() {
                public void run() {
                    if (verbose) {
                        System.out.println("Publishing staging log on shutdown...");
                    }
                    stagingLog.flushAndPublish();
                }
            });
        }
    }

    IBufferPublisher<Event> createCachePublisher() {
        BufferPublisher<Event> publisher = new BufferPublisher<Event>(hostName, tags);
        if (null != s3Client) {
            if (verbose) {
                System.out.println("Registering S3 publish helper");
            }
            publisher.addHelper(new S3PublishHelper(s3Client, s3.getBucket(), s3.getPath(),
                    s3Compression));
        }

        return publisher;
    }

    IBufferMonitor<Event> createCacheMonitor() {
        IBufferMonitor<Event> monitor = new CapacityBasedBufferMonitor<Event>(stagingBufferSize);
        if (0 < stagingBufferAge) {
            monitor = new TimePeriodBasedBufferMonitor<Event>(stagingBufferAge);
        }
        if (verbose) {
            System.out.println(String.format("Using cache monitor: %s", monitor.toString()));
        }
        return monitor;
    }

    Event mapToEvent(LoggingEvent event) {
        String message = null;
        if (null != getLayout()) {
            message = getLayout().format(event);
        } else {
            message = event.getMessage().toString();
        }
        Event mapped = new Event(event.getLoggerName(), event.getLevel().toString(), message);
        return mapped;
    }
}
