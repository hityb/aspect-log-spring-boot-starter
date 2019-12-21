package net.jutil.aspect.log;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 默认的Aop日志处理工具
 *
 * @author TYB
 * @date 2019/12/21
 */
public class DefaultAopLogHandle implements AopLogHandle {

    private LogLevel logLevel;
    private Object logger;
    AopJson aopJson = new AopJson();

    public DefaultAopLogHandle(LogLevel logLevel) {
        this.logLevel = logLevel;

    }

    @Override
    public void handle(Class clazz, String methodName, Object[] args, Object result) {
        StringBuilder sb = new StringBuilder("\nmethod       ==> ").append(methodName);
        for (int i = 0; i < args.length; i++) {
            Object argObj = args[i];
            sb.append("\nparameter").append(i).append(i < 100? (i < 10 ? "  ":" ") :"").append(" ==> ");
            if (argObj == null) {
                sb.append("null");
            } else {
                if (args[i] instanceof Serializable) {
                    sb.append(aopJson.toJsonString(args[i]));
                } else {
                    sb.append(args[i].toString());
                }
            }
            sb.append("\n");
        }
        sb.append("result       ==> ");
        if (result == null) {
            sb.append("null");
        } else {
            if (result instanceof Serializable) {
                sb.append(aopJson.toJsonString(result));
            } else {
                sb.append(result.toString());
            }
        }
        print(clazz, sb.toString());
    }


    /**
     * 打印输出日志，先获取logger,如果项目中没有logger，则使用系统打印(System.out.println())
     *
     * @param clazz clazz
     * @param str   str
     */
    private void print(Class clazz, String str) {
        Method logger = getLogger(clazz);
        if (logger == null) {
            System.out.println(str);
        } else {
            try {
                logger.invoke(this.logger, str);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 获取日志记录器，支持slf4j
     *
     * @param clazz clazz
     * @return {@link Method}
     */
    private Method getLogger(Class clazz) {
        try {
            Class loggerFactory = Class.forName("org.slf4j.LoggerFactory");
            Method getLogger = loggerFactory.getMethod("getLogger", Class.class);
            logger = getLogger.invoke(null, clazz);
            return logger.getClass().getMethod(logLevel.getLevel(), String.class);
        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

}
