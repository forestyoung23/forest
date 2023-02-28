package com.forest.registry;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryForever;
import org.apache.curator.x.discovery.ServiceDiscovery;
import org.apache.curator.x.discovery.ServiceDiscoveryBuilder;

/**
 * @author Forest Dong
 * @date 2022年08月28日 15:50
 */
public abstract class AbstractRegistryService implements RegistryService {
    protected CuratorFramework client;

    protected ServiceDiscovery discovery;

    public AbstractRegistryService(String connectString) throws Exception {
        client = CuratorFrameworkFactory.newClient(connectString, new RetryForever(100));
        client.start();
        discovery = ServiceDiscoveryBuilder.builder(Object.class).basePath("/forest").client(client).build();
        discovery.start();
    }
}
