package com.forest.ser.kryo;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.forest.ser.Decoder;

/**
 * @author Forest Dong
 * @date 2023年01月10日 15:38
 */
public class KryoDecoder implements Decoder {
    private Kryo kryo;

    public KryoDecoder() {
        kryo = new Kryo();
        kryo.setRegistrationRequired(false);
    }

    /**
     * 反序列化
     *
     * @param bytes
     * @param clazz
     * @return
     * @author Forest Dong
     * @date 2022/6/30 下午11:06
     */
    @Override
    public <T> T decode(byte[] bytes, Class<T> clazz) {
        Input input = new Input(bytes);
        // 这种用法会报错：com.esotericsoftware.kryo.io.KryoBufferUnderflowException: Buffer underflow.
        // kryo.readObject(input, clazz);
        return (T) kryo.readClassAndObject(input);
    }
}
