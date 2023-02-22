package com.forest.server;

import com.forest.ser.Decoder;
import com.forest.ser.Encoder;
import com.forest.ser.hessian.HessianDecoder;
import com.forest.ser.hessian.HessianEncoder;
import com.forest.ser.json.JsonDecoder;
import com.forest.ser.json.JsonEncoder;
import com.forest.ser.kryo.KryoDecoder;
import com.forest.ser.kryo.KryoEncoder;
import com.forest.transport.HttpTransportServer;
import com.forest.transport.TransportServer;
import lombok.Data;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * server配置
 *
 * @author Forest Dong
 * @date 2022年07月03日 14:33
 */
@Data
@Configuration
public class RpcServerConfig {
    private Class<? extends TransportServer> transportClass = HttpTransportServer.class;

    private String seriaType = "json";

    private int port = 3000;

    private static final Map<String, Class<? extends Encoder>> encoderMap =
            Map.of("kryo", KryoEncoder.class, "hessian", HessianEncoder.class, "json", JsonEncoder.class);
    private static final Map<String, Class<? extends Decoder>> decoderMap =
            Map.of("kryo", KryoDecoder.class, "hessian", HessianDecoder.class, "json", JsonDecoder.class);


    public Class<? extends Encoder> getEncoderClass() {
        return encoderMap.get(this.seriaType);
    }

    public Class<? extends Decoder> getDecoderClass() {
        return decoderMap.get(this.seriaType);
    }
}
