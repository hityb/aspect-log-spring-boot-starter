package net.jutil.aspect.log;

/**
 * Aop日志处理器
 *
 * @author TYB
 * @date 2019/12/21
 */
public interface AopLogHandle {

    /**
     * 处理函数，具体的处理过程在此方法中实现
     *
     * @param clazz      切点所对应的类
     * @param methodName 切点方法名
     * @param args       入参
     * @param result     出参
     */
    void handle(Class clazz, String methodName, Object[] args, Object result);
}
