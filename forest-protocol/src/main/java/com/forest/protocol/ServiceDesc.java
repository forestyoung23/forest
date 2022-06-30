package com.forest.protocol;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 表示服务
 *
 * @author Forest Dong
 * @date 2022年06月30日 22:24
 */
@Data
@AllArgsConstructor
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
}
