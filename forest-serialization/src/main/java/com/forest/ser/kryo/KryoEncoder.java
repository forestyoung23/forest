package com.forest.ser.kryo;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Output;
import com.forest.ser.Encoder;

/**
 * @author Forest Dong
 * @date 2023年01月10日 15:38
 */
public class KryoEncoder implements Encoder {
    private Kryo kryo;

    public KryoEncoder() {
        kryo = new Kryo();
        kryo.setRegistrationRequired(false);
    }

    /**
     * 序列化
     *
     * @param obj
     * @return
     * @author Forest Dong
     * @date 2022/6/30 下午11:05
     */
    @Override
    public byte[] encode(Object obj) {
        try (Output output = new Output(128, -1)) {
            kryo.writeClassAndObject(output, obj);
            return output.toBytes();
        }

    }
}
