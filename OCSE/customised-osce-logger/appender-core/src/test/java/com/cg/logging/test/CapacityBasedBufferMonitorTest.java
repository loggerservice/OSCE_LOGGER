package com.cg.logging.test;

import org.junit.Assert;
import org.junit.Test;

import com.cg.logging.CapacityBasedBufferMonitor;

public class CapacityBasedBufferMonitorTest extends AbstractBufferMonitorTest {

    @Test
    @SuppressWarnings("unchecked")
    public void testFlush() {
        CapacityBasedBufferMonitor monitor = new CapacityBasedBufferMonitor(5);
        for (int i = 0; i < 25; i++) {
            monitor.eventAdded(null, publisher);
        }
        Assert.assertEquals("5 batches of 5 events per batch", 5, publishCount);
    }

}
