package cn.wolfcode.aspect;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * v1.0
 * Created by wz on 2019/11/23.
 */
@Aspect
@Component
public class ParameterInterceptor {

    private final Logger log= LogManager.getLogger(this.getClass());
    @Around("execution(* cn.wolfcode.service.impl.*.*(..))")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        long time = System.currentTimeMillis();
        log.info("========================================= begin service >>>");
        log.info("Method >>> {} {}()", point.getTarget(), point.getSignature().getName());
        Object[] args = point.getArgs();
        if (args != null) {
            for (Object obj : args) {
                if (obj == null) continue;
                log.info("Parameter >>> [{}]:[{}]", obj.getClass().getSimpleName(), obj);
            }
        }

        Object returnObj = point.proceed(args);
        if (returnObj == null) {
            log.info("Return >>> null");
        } else {
            log.info("Return >>> [{}]:[{}]", returnObj.getClass().getSimpleName(), returnObj);
        }

        log.info("Proceeding >>> [{}ms]", System.currentTimeMillis() - time);

        return returnObj;
    }
}
