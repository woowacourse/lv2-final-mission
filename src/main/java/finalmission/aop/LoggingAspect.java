package finalmission.aop;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LoggingAspect {

    private static final Logger log = LoggerFactory.getLogger(LoggingAspect.class);

    @Pointcut("execution(* finalmission.controller..*.*(..))")
    public void allController() {
    }

    @Pointcut("execution(* finalmission.client..*.*(..))")
    public void allExternalApiClient() {
    }

    @Around("allController()")
    public Object logAllRequestAndResponse(ProceedingJoinPoint joinPoint) throws Throwable {
        List<String> args = Arrays.stream(joinPoint.getArgs()).map(Object::toString).toList();
        log.info("[INFO][{}][IN][{}] {}", LocalDateTime.now(), joinPoint.getSignature(), String.join(",", args));
        Object returnValue = joinPoint.proceed();
        log.info("[INFO][{}][OUT][{}] {}", LocalDateTime.now(), joinPoint.getSignature(), returnValue);
        return returnValue;
    }

    @Around("allExternalApiClient()")
    public Object logAllExternalApiRequest(ProceedingJoinPoint joinPoint) throws Throwable {
        List<String> args = Arrays.stream(joinPoint.getArgs()).map(Object::toString).toList();
        log.info("[INFO][{}][IN][{}] {}", LocalDateTime.now(), joinPoint.getSignature(), String.join(",", args));
        Object returnValue = joinPoint.proceed();
        log.info("[INFO][{}][OUT][{}] {}", LocalDateTime.now(), joinPoint.getSignature(), returnValue);
        return returnValue;
    }
}
