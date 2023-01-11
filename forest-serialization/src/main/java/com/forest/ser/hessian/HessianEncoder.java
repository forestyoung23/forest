package com.forest.ser.hessian;

import com.caucho.hessian.io.Hessian2Output;
import com.forest.ser.Encoder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @author Forest Dong
 * @date 2023年01月09日 17:49
 */
public class HessianEncoder implements Encoder {
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
        byte[] data;
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            Hessian2Output output = new Hessian2Output(outputStream);
            output.writeObject(obj);
            output.getBytesOutputStream().flush();
            output.completeMessage();
            output.close();
            data = outputStream.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return data;
    }
}
