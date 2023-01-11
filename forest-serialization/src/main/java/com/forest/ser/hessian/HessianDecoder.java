package com.forest.ser.hessian;

import com.caucho.hessian.io.Hessian2Input;
import com.forest.ser.Decoder;

import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * @author Forest Dong
 * @date 2023年01月09日 17:49
 */
public class HessianDecoder implements Decoder {
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
        T result;
        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
            Hessian2Input input = new Hessian2Input(inputStream);
            result = (T) input.readObject();
            input.completeMessage();
            input.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }
}
