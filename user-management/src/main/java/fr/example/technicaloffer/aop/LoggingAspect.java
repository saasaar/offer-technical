package fr.example.technicaloffer.aop;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
/**
 * AOP for logging APIs call
 * @author saad arkoubi 
 *
 */
@Slf4j
@Component
@Aspect
public class LoggingAspect {

	private static final String SEPARATEUR = "=============================";

	@Pointcut("execution (* fr.example.technicaloffer.rest..*(..)) && "
			+ "!execution (* fr.example.technicaloffer.rest.exception..*(..))")
	public void restController() {
	}

	@Pointcut("within(fr.example.technicaloffer.rest.exception.*)")
	public void restExceptionHandler() {
	}

	@Before("restController()")
	public void logBefore(JoinPoint joinPoint) {
		log.info(SEPARATEUR);
		log.info(new StringBuilder("Action triggred: ").append(joinPoint.getSignature().getDeclaringTypeName())
				.append(".").append(joinPoint.getSignature().getName()).append(", ").append("inputs :  ")
				.append(Arrays.toString(joinPoint.getArgs())).toString());
	}

	@Before("restExceptionHandler()")
	public void logBeforeExceptionHandler(JoinPoint joinPoint) {
		log.info(new StringBuilder("Exception handler triggred : ")
				.append(joinPoint.getSignature().getDeclaringTypeName()).append(".")
				.append(joinPoint.getSignature().getName()).toString());
		Arrays.asList(joinPoint.getArgs()).stream().forEach(
				arg -> log.error(new StringBuilder("Exception occured : ").append((Exception) arg).toString()));
	}

	@Around("restController() || restExceptionHandler()")
	public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {

		long startExecution = System.currentTimeMillis();
		try {
			Object result = joinPoint.proceed();
			long elapsedTime = System.currentTimeMillis() - startExecution;
			return (result instanceof Mono<?>) ? ((Mono<?>) result).doOnSuccess(o -> logResult(o, elapsedTime)) : logResult(result, elapsedTime); 
		} catch (IllegalArgumentException e) {
			log.error(e.getMessage());
			throw e;
		}
	}

	private Object logResult(Object value, long elapsedTime) {
		log.info(new StringBuilder("Return value : ").append(value).append(". ").append("Execution time : ")
				.append(elapsedTime).append(" ms").toString());
		return value;
	}

}
