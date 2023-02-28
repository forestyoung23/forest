package com.forest.server;

import com.forest.common.ReflectionUtils;
import com.forest.protocol.Request;
import com.forest.protocol.Response;
import com.forest.protocol.ServiceDesc;
import com.forest.ser.Decoder;
import com.forest.ser.Encoder;
import com.forest.transport.RequestHandler;
import com.forest.transport.TransportServer;
import com.forest.zkclient.ZookeeperRegistry;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;

/**
 * @author Forest Dong
 * @date 2022年07月03日 15:42
 */
@Slf4j
public class RpcServer {
    private RpcServerConfig config;
    private TransportServer net;
    private Encoder encoder;
    private Decoder decoder;
    private ServiceManager serviceManager;
    private ServiceInvoker serviceInvoker;

    private ZookeeperRegistry registry;

    public RpcServer() {
        this(new RpcServerConfig());
    }

    public RpcServer(RpcServerConfig config) {
        this.config = config;
        this.net = ReflectionUtils.newInstance(config.getTransportClass());
        this.net.init(config.getPort(), this.handler);
        this.encoder = ReflectionUtils.newInstance(config.getEncoderClass());
        this.decoder = ReflectionUtils.newInstance(config.getDecoderClass());
        this.serviceManager = new ServiceManager();
        this.serviceInvoker = new ServiceInvoker();
        try {
            this.registry = setUp();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 注册服务
     *
     * @author Forest Dong
     * @date 2023/02/28 18:37
     */
    private ZookeeperRegistry setUp() throws Exception {
        return new ZookeeperRegistry("127.0.0.1:2181");
    }


    public <T> void registerZk(Class<T> interfaceClass, T bean) throws Exception {

        Method[] methods = ReflectionUtils.getPublicMethods(interfaceClass);
        for (Method method : methods) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("method", method);
            map.put("target", bean);
            org.apache.curator.x.discovery.ServiceInstance<Object> instance = org.apache.curator.x.discovery.ServiceInstance.builder()
                    .name(interfaceClass.getName() + method.getName())
                    .address(Inet4Address.getLocalHost().getHostAddress())
                    .port(3002).registrationTimeUTC(System.currentTimeMillis()).payload().build();
            ServiceInstance instance = new ServiceInstance(bean, method);
            ServiceDesc serviceDesc = ServiceDesc.from(interfaceClass, method);
//            services.put(serviceDesc, instance);
//            log.info("register service:{} {}", serviceDesc.getClazz(), serviceDesc.getMethod());
        }

//        registry.registry(instance);
    }

    public <T> void register(Class<T> interfaceClass, T bean) {
        serviceManager.register(interfaceClass, bean);
    }

    public void start() {
        net.start();
    }

    public void stop() {
        net.stop();
    }

    private RequestHandler handler = new RequestHandler() {
        @Override
        public void onRequest(InputStream is, OutputStream os) {
            Response response = new Response();
            try {
                byte[] inBytes = new byte[is.available()];
                IOUtils.readFully(is, inBytes, 0, is.available());
                Request request = decoder.decode(inBytes, Request.class);
                log.info("get request: {}", request);
                ServiceInstance instance = serviceManager.lookup(request);
                Object invoke = serviceInvoker.invoke(instance, request);
                response.setData(invoke);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                response.setCode(1);
                response.setMessage("RpcServer got error: " + e.getClass().getName() + " : " + e.getMessage());
            } finally {
                byte[] encode = encoder.encode(response);
                try {
                    os.write(encode);
                    log.info("responsed client");
                } catch (IOException e) {
                    log.error(e.getMessage(), e);
                }
            }

        }
    };

}
