package com.forest.server;

import com.forest.common.ReflectionUtils;
import com.forest.protocol.Request;
import com.forest.protocol.ServiceDesc;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 管理rpc暴露的服务
 *
 * @author Forest Dong
 * @date 2022年07月03日 14:44
 */
@Slf4j
public class ServiceManager {
    private Map<ServiceDesc, ServiceInstance> services;

    public ServiceManager() {
        this.services = new ConcurrentHashMap<>();
    }

    public <T> void register(Class<T> interfaceClass, T bean) {
        Method[] methods = ReflectionUtils.getPublicMethods(interfaceClass);
        for (Method method : methods) {
            ServiceInstance instance = new ServiceInstance(bean, method);
            ServiceDesc serviceDesc = ServiceDesc.from(interfaceClass, method);
            services.put(serviceDesc, instance);
            log.info("register service:{} {}", serviceDesc.getClazz(), serviceDesc.getMethod());
        }
    }

    public ServiceInstance lookup(Request request) {
        ServiceDesc service = request.getService();
        return services.get(service);
    }
}
