package com.forest.server;

import com.forest.common.ReflectionUtils;
import com.forest.protocol.Request;
import com.forest.protocol.Response;
import com.forest.ser.Decoder;
import com.forest.ser.Encoder;
import com.forest.transport.RequestHandler;
import com.forest.transport.TransportServer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

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
