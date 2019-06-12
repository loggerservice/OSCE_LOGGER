package com.cg.osce.logging;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.cg.osce.logging.model.Log;
import com.cg.osce.logging.utils.Utils;

@Component
public class OSCELogger {

    private static Logger logger = Logger.getLogger(OSCELogger.class.getName());;

    // To Log Requests
    public void log(String userId, String serverName, String application, String module,
            String operation, Level level, Object[] requestParams, String processId) {
        Log log = new Log();

        log.setUserId(userId);
        log.setServerName(serverName);
        log.setApplication(application);
        log.setModule(module);
        log.setOperation(operation);
        log.setMessageType("REQUEST");
        log.setRequestParams(requestParams);
        log.setProcessId(processId);

        String logMessage = Utils.getLoggableString(log);

        writeMessage(logMessage, level);

    }

    // To Log Response
    public void log(String userId, String serverName, String application, String module,
            String operation, Level level, Object response, String processId, int httpStatusCode) {

        Log log = new Log();

        log.setUserId(userId);
        log.setProcessId(processId);
        log.setServerName(serverName);
        log.setApplication(application);
        log.setModule(module);
        log.setOperation(operation);
        log.setMessageType("RESPONSE");
        log.setResponsePayload(response);
        log.setHttpStatusCode(httpStatusCode);
        String logMessage = Utils.getLoggableString(log);

        writeMessage(logMessage, level);

    }

    // To Log Events
    public void log(String userId, String application, String module, Level level,
            String messageType, String processId, String status) {

        Log log = new Log();
        String logMessage = Utils.getLoggableString(log);

        writeMessage(logMessage, level);

    }

    // To Log Exception
    public void log(String userId, String serverName, String application, String module,
            String operation, Level level, int exceptionCode, String processId,
            String exceptionDetails, Object stackTrace) {

        Log log = new Log();

        log.setUserId(userId);
        log.setProcessId(processId);
        log.setServerName(serverName);
        log.setApplication(application);
        log.setModule(module);
        log.setOperation(operation);
        log.setMessageType("EXCEPTION");
        log.setExceptionCode(exceptionCode);
        log.setExceptionDetails(exceptionDetails);
        log.setStackTrace(stackTrace);
        String logMessage = Utils.getLoggableString(log);

        writeMessage(logMessage, level);

    }

    private void writeMessage(String logMessage, Level level) {

        if (Level.DEBUG == level)
            logger.debug(logMessage);
        else if (Level.INFO == level)
            logger.info(logMessage);
        else if (Level.ERROR == level)
            logger.error(logMessage);
        else if (Level.WARN == level)
            logger.warn(logMessage);
        else if (Level.TRACE == level)
            logger.trace(logMessage);
    }
}
