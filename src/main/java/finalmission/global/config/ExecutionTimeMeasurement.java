package finalmission.global.config;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class ExecutionTimeMeasurement {

    @Pointcut("@annotation(finalmission.global.config.ExecutionTimeAnnotation)")
    private void timer() {
    }

    @Around("timer()")
    public Object AssumeExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object proceed = joinPoint.proceed();
        long endTime = System.currentTimeMillis();

        long duration = endTime - startTime;
        String methodName = joinPoint.getSignature().toShortString();

        log.info("[실행 시간] 메서드: {} 소요: {}ms", methodName, duration);
        return proceed;
    }
}
