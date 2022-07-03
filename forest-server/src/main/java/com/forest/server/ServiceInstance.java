package com.forest.server;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.lang.reflect.Method;

/**
 * 表示一个具体的服务
 *
 * @author Forest Dong
 * @date 2022年07月03日 14:43
 */
@Data
@AllArgsConstructor
public class ServiceInstance {
    private Object target;

    private Method method;
}
