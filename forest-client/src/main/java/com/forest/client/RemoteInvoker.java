package com.forest.client;

import com.forest.protocol.Request;
import com.forest.protocol.Response;
import com.forest.protocol.ServiceDesc;
import com.forest.ser.Decoder;
import com.forest.ser.Encoder;
import com.forest.transport.TransportClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author Forest Dong
 * @date 2022年07月03日 16:39
 */
@Slf4j
public class RemoteInvoker implements InvocationHandler {
    private Class clazz;
    private Encoder encoder;
    private Decoder decoder;
    private TransportSelector selector;

    public RemoteInvoker(Class clazz, Encoder encoder, Decoder decoder, TransportSelector selector) {
        this.clazz = clazz;
        this.encoder = encoder;
        this.decoder = decoder;
        this.selector = selector;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Request request = new Request();
        request.setService(ServiceDesc.from(clazz, method));
        request.setParams(args);
        Response response = invokeRemote(request);
        if (null == response || response.getCode() != 0) {
            throw new IllegalStateException("faild to invoke remote: " + response);
        }
        return response.getData();
    }

    private Response invokeRemote(Request request) {
        TransportClient client = null;
        Response response;
        try {
            client = selector.select();
            byte[] out = encoder.encode(request);
            InputStream inputStream = client.write(new ByteArrayInputStream(out));
            byte[] inBytes = new byte[inputStream.available()];
            IOUtils.readFully(inputStream, inBytes, 0, inputStream.available());
            response = decoder.decode(inBytes, Response.class);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            response = new Response();
            response.setCode(1);
            response.setMessage("RpcClient got error: " + e.getClass() + e.getMessage());
        } finally {
            if (null != client) {
                selector.release(client);
            }
        }
        return response;
    }
}
