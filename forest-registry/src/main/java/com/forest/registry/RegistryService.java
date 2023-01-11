package com.forest.registry;

import org.apache.curator.x.discovery.ServiceInstance;

import java.util.List;

/**
 * @author Forest Dong
 * @date 2022年08月28日 11:14
 */
public interface RegistryService {
    /**
     * 服务注册
     *
     * @param
     * @return
     * @author Forest Dong
     * @date 2021/10/7 下午3:13
     */
    void registry(ServiceInstance instance) throws Exception;

    /**
     * 注销服务
     *
     * @param
     * @return
     * @author Forest Dong
     * @date 2022/8/28 下午4:07
     */
    void unRegistry(ServiceInstance instance) throws Exception;

    /**
     * 查找服务
     *
     * @param
     * @return
     * @author Forest Dong
     * @date 2022/8/28 下午4:08
     */
    List<ServiceInstance> lookUp(String name) throws Exception;
}
