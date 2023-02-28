package com.forest.client;

import com.forest.common.ReflectionUtils;
import com.forest.ser.Decoder;
import com.forest.ser.Encoder;
import com.forest.zkclient.ZookeeperRegistry;
import org.apache.curator.x.discovery.ServiceInstance;
import org.apache.curator.x.discovery.ServiceInstanceBuilder;

import java.lang.reflect.Proxy;
import java.net.Inet4Address;
import java.util.HashMap;
import java.util.List;

/**
 * @author Forest Dong
 * @date 2022年07月03日 16:27
 */
public class RpcClient {
    private RpcClientConfig config;
    private Encoder encoder;
    private Decoder decoder;
    private TransportSelector selector;

    public RpcClient() {
        this(new RpcClientConfig());
    }

    public RpcClient(RpcClientConfig config) {
        this.config = config;
        this.encoder = ReflectionUtils.newInstance(config.getEncoderClass());
        this.decoder = ReflectionUtils.newInstance(config.getDecoderClass());
        this.selector = ReflectionUtils.newInstance(config.getSelectorClass());
        this.selector.init(config.getServers(), config.getConnectCount(), config.getTransportClass());
    }

    public <T> T getProxy(Class<T> clazz) {
        return (T) Proxy.newProxyInstance(getClass().getClassLoader(), new Class[]{clazz}, new RemoteInvoker(clazz, encoder, decoder, selector));
    }
}
