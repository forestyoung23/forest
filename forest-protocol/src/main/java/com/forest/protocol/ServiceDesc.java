package com.forest.protocol;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;

/**
 * 表示服务
 *
 * @author Forest Dong
 * @date 2022年06月30日 22:24
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceDesc {
    /**
     * 服务的类名
     */
    private String clazz;

    /**
     * 服务的方法名
     */
    private String method;

    /**
     * 返回类型
     */
    private String returnType;

    /**
     * 参数类型
     */
    private String[] paramsTypes;

    public static ServiceDesc from(Class clazz, Method method) {
        ServiceDesc desc = new ServiceDesc();
        desc.setClazz(clazz.getName());
        desc.setMethod(method.getName());
        desc.setReturnType(method.getReturnType().getName());
        Class<?>[] types = method.getParameterTypes();
        String[] paramsTypes = new String[types.length];
        for (int i = 0; i < types.length; i++) {
            paramsTypes[i] = types[i].getName();
        }
        desc.setParamsTypes(paramsTypes);
        return desc;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ServiceDesc that = (ServiceDesc) o;
        return Objects.equals(clazz, that.clazz) &&
                Objects.equals(method, that.method) &&
                Objects.equals(returnType, that.returnType) &&
                Arrays.equals(paramsTypes, that.paramsTypes);
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

    @Override
    public String toString() {
        return "clazz=" + clazz + ",method=" + method + "，returnType=" + returnType + ",parameterTypes=" + Arrays.toString(paramsTypes);
    }
}
