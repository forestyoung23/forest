package com.forest.server;

import com.forest.ser.Decoder;
import com.forest.ser.Encoder;
import com.forest.ser.JsonDecoder;
import com.forest.ser.JsonEncoder;
import com.forest.transport.HttpTransportServer;
import com.forest.transport.TransportServer;
import lombok.Data;

/**
 * server配置
 *
 * @author Forest Dong
 * @date 2022年07月03日 14:33
 */
@Data
public class RpcServerConfig {
    private Class<? extends TransportServer> transportClass = HttpTransportServer.class;
    private Class<? extends Encoder> encoderClass = JsonEncoder.class;
    private Class<? extends Decoder> decoderClass = JsonDecoder.class;
    private int port = 3000;
}
