package zerobase.weather.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import zerobase.weather.WeatherApplication;

@Aspect
@Component
public class LogAspect {

    public static final Logger logger =
            LoggerFactory.getLogger(WeatherApplication.class);

    @Around("@annotation(zerobase.weather.aop.TraceLog)")
    public Object doTransaction(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        String methodInfo =
                joinPoint.getSignature().getDeclaringTypeName() + "."
                        + joinPoint.getSignature().getName();

        logger.info("[start][{}] args={}", methodInfo, args);

        try {
            Object result = joinPoint.proceed(args);

            logger.info("[end][{}]", methodInfo);

            return result;
        } catch (Throwable e) {
            logger.error("error! {}", joinPoint.getSignature(), e);
            throw new RuntimeException(e);
        }
    }

}
