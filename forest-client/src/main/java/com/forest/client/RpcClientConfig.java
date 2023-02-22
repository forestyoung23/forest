package com.forest.client;

import com.forest.protocol.Peer;
import com.forest.ser.Decoder;
import com.forest.ser.Encoder;
import com.forest.ser.hessian.HessianDecoder;
import com.forest.ser.hessian.HessianEncoder;
import com.forest.ser.json.JsonDecoder;
import com.forest.ser.json.JsonEncoder;
import com.forest.ser.kryo.KryoDecoder;
import com.forest.ser.kryo.KryoEncoder;
import com.forest.transport.HttpTransportClient;
import com.forest.transport.TransportClient;
import lombok.Data;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author Forest Dong
 * @date 2022年07月03日 16:23
 */
@Data
@Configuration
public class RpcClientConfig {
    private Class<? extends TransportClient> transportClass = HttpTransportClient.class;

    private String seriaType = "json";
    private Class<? extends TransportSelector> selectorClass = RandomTransportSelector.class;
    private int connectCount = 1;

    private List<Peer> servers = Arrays.asList(new Peer("localhost", 3000));

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
