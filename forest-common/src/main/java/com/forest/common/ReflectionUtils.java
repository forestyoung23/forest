package com.forest.common;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;

/**
 * @author Forest Dong
 * @date 2022年06月30日 22:32
 */
public class ReflectionUtils {
    /**
     * 创建对象
     *
     * @param clazz
     * @return
     * @author Forest Dong
     * @date 2022/6/30 下午10:42
     */
    public static <T> T newInstance(Class<T> clazz){
        try {
            return clazz.getConstructor().newInstance();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * 获取类的public方法
     *
     * @param clazz
     * @return
     * @author Forest Dong
     * @date 2022/6/30 下午10:43
     */
    public static Method[] getPublicMethods(Class clazz) {
        Method[] methods = clazz.getDeclaredMethods();
        ArrayList<Method> result = new ArrayList<>();
        for (Method method : methods) {
            if (Modifier.isPublic(method.getModifiers())) {
                result.add(method);
            }
        }
        return result.toArray(new Method[0]);
    }

    /**
     * 调用指定对象的指定方法
     *
     * @param obj
     * @param method
     * @param args
     * @return
     * @author Forest Dong
     * @date 2022/6/30 下午10:45
     */
    public static Object invoke(Object obj, Method method, Object... args) {
        try {
            return method.invoke(obj, args);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

}
