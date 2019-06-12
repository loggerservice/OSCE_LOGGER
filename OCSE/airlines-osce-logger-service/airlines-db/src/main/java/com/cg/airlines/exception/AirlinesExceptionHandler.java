package com.cg.airlines.exception;

import org.apache.log4j.Level;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.cg.osce.logging.OSCELogger;

public class AirlinesExceptionHandler {
    OSCELogger oscelogger = new OSCELogger();
    private final Logger slf4jLogger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(value = AirlinesException.class)
    public ResponseEntity<AirlinesExceptionResult> handleBaseException(AirlinesException e) {

        slf4jLogger.error("Checked exception occurred", e);
        AirlinesExceptionResult result = new AirlinesExceptionResult(e.getCode(), e.getMessage());
        oscelogger.log("111", "serverName", "application", "module", "operation", Level.ERROR, e
                .getCode(), "1213", e.getMessage(), e.getStackTrace());
        
        if (e.getCode() == 204) {
            return new ResponseEntity<>(result, HttpStatus.NO_CONTENT);
        } else if (e.getCode() == 400) {
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
