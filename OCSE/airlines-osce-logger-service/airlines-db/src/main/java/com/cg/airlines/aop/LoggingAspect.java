package com.cg.airlines.aop;

import org.apache.log4j.Level;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import com.cg.airlines.exception.AirlinesException;
import com.cg.osce.logging.OSCELogger;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@Aspect
@Configuration
@Component
public class LoggingAspect {

    @Autowired
    OSCELogger osceLogger;

    private ObjectMapper mapper = new ObjectMapper();

    @Before("execution(* com.cg.airlines.service.FlightService..*(..))")
    public void controllerLogging(JoinPoint joint) throws AirlinesException {
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        osceLogger.log("1234", "localhost", "AirlinesReservationSystem", "Services", joint
                .getStaticPart().getSignature().getName(), Level.INFO, joint.getArgs(), "1234");

    }

    @AfterReturning(pointcut = "execution(* com.cg.airlines.service.FlightService..*(..))", returning = "result")
    public void serviceSetterMethodLogging(JoinPoint joint, Object result)
            throws AirlinesException {
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

        osceLogger.log("1234", "localhost", "AirlinesReservationSystem", "Services", joint
                .getStaticPart().getSignature().getName(), Level.INFO, result, "1234", 200);

    }

}
