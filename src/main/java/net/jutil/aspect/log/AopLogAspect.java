package net.jutil.aspect.log;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;


/**
 * Aop日志方面
 *
 * @author TYB
 * @date 2019/11/11
 */
@Aspect
@Component
public class AopLogAspect {

    private final AopLogHandle aopLogHandle;

    public AopLogAspect(AopLogHandle aopLogHandle) {
        this.aopLogHandle = aopLogHandle;
    }


    /**
     * AopLog切点
     */
    @Pointcut("@annotation(AopLog)")
    public void annotationPointCut() {
    }


    /**
     * 配置环绕通知,使用在方法aspect()上注册的切入点
     *
     * @param point 切点
     * @return {@link Object}* @throws Throwable Throwable
     */
    @Around("annotationPointCut()")
    public Object doAround(ProceedingJoinPoint point) throws Throwable {

        Object result = point.proceed();

        //目标方法实体
        Method method = ((MethodSignature) point.getSignature()).getMethod();
        boolean hasMethodLogAnno = method.isAnnotationPresent(AopLog.class);
        //没加注解 直接执行返回结果
        if (!hasMethodLogAnno) {
            return result;
        }
        this.aopLogHandle.handle(point.getSignature().getDeclaringType(), point.getSignature().toString(), point.getArgs(), result);

        return result;
    }
}
