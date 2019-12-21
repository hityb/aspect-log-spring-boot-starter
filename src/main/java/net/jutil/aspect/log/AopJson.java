package net.jutil.aspect.log;

import org.springframework.util.ClassUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Aop Json
 * 通过classpath中已有的第三方json工具包来转换成json字符串的工具<br>
 *  支持<code>FastJson</code>、<code>Gson</code>两种包，两个工具包都存在的话，优先使用<code>FastJson</code>
 *
 * @author TYB
 * @date 2019/12/21
 */
public class AopJson {

    public static Method toStringMethod;

    public static Object instance = null;

    {
        //初始化，获得序列化json的反射方法
        toStringMethod = getFastJsonString();
        if (toStringMethod == null) {
            toStringMethod = getGsonString();
        }
    }


    /**
     * 转为Json字符串
     *
     * @param obj obj
     * @return {@link String}
     */
    public String toJsonString(Object obj) {
        try {
            if (toStringMethod == null) {
                throw new ClassNotFoundException("No json lib found");
            }
            return (String) toStringMethod.invoke(instance, obj);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取fastson toJsonString 反射方法
     *
     * @return {@link Method}
     */
    private Method getFastJsonString() {
        try {
            boolean present = ClassUtils.isPresent("com.alibaba.fastjson.JSON", this.getClass().getClassLoader());
            if (!present) {
                return null;
            }
            Class fastjson = Class.forName("com.alibaba.fastjson.JSON");
            Method fastjsonMethod = fastjson.getMethod("toJSONString", Object.class);
            return fastjsonMethod;
        } catch (ClassNotFoundException | NoSuchMethodException e) {
            return null;
        }
    }

    /**
     * 获取Gson toJson 反射方法
     *
     * @return {@link Method}
     */
    private Method getGsonString() {
        try {
            boolean present = ClassUtils.isPresent("com.google.gson.Gson", this.getClass().getClassLoader());
            if (!present) {
                return null;
            }
            Class gsonClass = Class.forName("com.google.gson.Gson");
            instance = gsonClass.getDeclaredConstructor().newInstance();
            Method gsonMethod = gsonClass.getMethod("toJson", Object.class);
            return gsonMethod;
        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            return null;
        }
    }
}
