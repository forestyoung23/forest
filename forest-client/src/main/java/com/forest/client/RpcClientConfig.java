package com.forest.client;

import com.forest.protocol.Peer;
import com.forest.ser.Decoder;
import com.forest.ser.Encoder;
import com.forest.ser.kryo.KryoDecoder;
import com.forest.ser.kryo.KryoEncoder;
import com.forest.transport.HttpTransportClient;
import com.forest.transport.TransportClient;
import lombok.Data;

import java.util.Arrays;
import java.util.List;

/**
 * @author Forest Dong
 * @date 2022年07月03日 16:23
 */
@Data
public class RpcClientConfig {
    private Class<? extends TransportClient> transportClass = HttpTransportClient.class;

    private Class<? extends Encoder> encoderClass = KryoEncoder.class;
    private Class<? extends Decoder> decoderClass = KryoDecoder.class;
    private Class<? extends TransportSelector> selectorClass = RandomTransportSelector.class;
    private int connectCount = 1;

    private List<Peer> servers = Arrays.asList(new Peer("localhost", 3000));
}
