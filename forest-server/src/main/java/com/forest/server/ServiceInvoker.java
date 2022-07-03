package com.forest.server;

import com.forest.common.ReflectionUtils;
import com.forest.protocol.Request;

/**
 * 调用具体服务
 *
 * @author Forest Dong
 * @date 2022年07月03日 15:39
 */
public class ServiceInvoker {
    public Object invoke(ServiceInstance instance, Request request) {
        return ReflectionUtils.invoke(instance.getTarget(), instance.getMethod(), request.getParams());
    }
}
