package com.forest.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryForever;
import org.apache.curator.x.discovery.ServiceDiscovery;
import org.apache.curator.x.discovery.ServiceDiscoveryBuilder;
import org.apache.curator.x.discovery.ServiceInstance;

/**
 * @author Forest Dong
 * @date 2022年08月28日 10:27
 */
public class ServerApp {

    public ServerApp() {
    }

    public static void main(String[] args) throws Exception {
        ServiceInstance<Object> test = ServiceInstance.builder().name("test").port(9090).address("127.0.0.1").registrationTimeUTC(System.currentTimeMillis()).build();
        CuratorFramework client = CuratorFrameworkFactory.newClient("127.0.0.1:2181", new RetryForever(100));
        client.start();
        ServiceDiscovery<Object> build = ServiceDiscoveryBuilder.builder(Object.class).basePath("/test").client(client).build();
        build.registerService(test);
        System.err.println(test);
        Thread.sleep(60000);
    }
}
