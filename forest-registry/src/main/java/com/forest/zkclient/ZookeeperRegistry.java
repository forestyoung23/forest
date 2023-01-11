package com.forest.zkclient;

import com.forest.registry.AbstractRegistryService;
import com.forest.registry.RegistryService;
import org.apache.curator.x.discovery.ServiceInstance;

import java.util.HashMap;
import java.util.List;

/**
 * @author Forest Dong
 * @date 2022年08月28日 15:46
 */
public class ZookeeperRegistry extends AbstractRegistryService {
    protected ZookeeperRegistry() throws Exception {
        super();
    }

    /**
     * 服务注册
     *
     * @param instance
     * @return
     * @author Forest Dong
     * @date 2021/10/7 下午3:13
     */
    @Override
    public void registry(ServiceInstance instance) throws Exception {
        discovery.registerService(instance);
    }

    @Override
    public void unRegistry(ServiceInstance instance) throws Exception {
        discovery.unregisterService(instance);
    }

    /**
     * 查找服务
     *
     * @param name@return
     * @author Forest Dong
     * @date 2022/8/28 下午4:08
     */
    @Override
    public List<ServiceInstance> lookUp(String name) throws Exception {
        return (List<ServiceInstance>) discovery.queryForInstances(name);
    }

    public static void main(String[] args) throws Exception {
        RegistryService service = new ZookeeperRegistry();
        HashMap<String, String> map = new HashMap<>();
        map.put("qw", "123");
        map.put("asd", "vef3");
        ServiceInstance<Object> instance = ServiceInstance.builder().name("测试").address("127.0.0.1").port(3002).registrationTimeUTC(System.currentTimeMillis()).payload(map).build();
        service.registry(instance);
        Thread.sleep(10000);
        List<ServiceInstance> list = service.lookUp("测试");
        for (ServiceInstance serviceInstance : list) {
            System.err.println(serviceInstance);

        }
        Thread.sleep(60000);
    }
}
