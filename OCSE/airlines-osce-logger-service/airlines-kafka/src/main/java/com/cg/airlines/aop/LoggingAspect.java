package com.cg.airlines.aop;

import org.apache.log4j.Level;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import com.cg.airlines.exception.AirlinesException;
import com.cg.osce.logging.OSCELogger;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import antlr.Utils;

@Aspect
@Configuration
public class LoggingAspect {

	OSCELogger osceLogger = new OSCELogger();

	Utils utils = new Utils();
	private final Logger slf4jLogger = LoggerFactory.getLogger(this.getClass());

	private ObjectMapper mapper = new ObjectMapper();

	@Before("execution(* com.cg.airlines.service.FlightService..*(..))")
	public void controllerLogging(JoinPoint joint) throws AirlinesException {
		mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		try {
			osceLogger.log("1234", "localhost", "AirlinesReservationSystem", "Services",
					joint.getStaticPart().getSignature().getName(), Level.INFO, joint.getArgs(), "1234");
			slf4jLogger.info("Begin of - " + joint.getStaticPart().getSignature().getName() + " method");
			slf4jLogger.info("Info Input Parameters -:\n "
					+ mapper.writerWithDefaultPrettyPrinter().writeValueAsString(joint.getArgs()));
		} catch (JsonProcessingException e) {
		}	

	}

	/*
	 * @AfterReturning(pointcut =
	 * "execution(*com.cg.airlines.service.FlightService.*(..))", returning =
	 * "result") public void serviceSetterMethodLogging(JoinPoint joint, Object
	 * result) throws AirlinesException {
	 * mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);`
	 * 
	 * osceLogger.log("1234", "localhost", "AirlinesReservationSystem", "Services",
	 * joint.getStaticPart().getSignature().getName(), Level.INFO, result, "1234",
	 * 200);
	 * 
	 * try {
	 * 
	 * slf4jLogger.info("Info Output Parameters -: \n " +
	 * mapper.writerWithDefaultPrettyPrinter().writeValueAsString(null != result ?
	 * result : "")); slf4jLogger.debug("end of  - " +
	 * joint.getStaticPart().getSignature().getName() + " method"); } catch
	 * (JsonProcessingException e) { throw new AirlinesException(400,
	 * e.getMessage()); } }
	 */

}
