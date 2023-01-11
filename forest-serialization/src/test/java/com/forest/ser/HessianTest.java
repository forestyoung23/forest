package com.forest.ser;

import com.caucho.hessian.io.Hessian2Output;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * @author Forest Dong
 * @date 2023年01月09日 16:05
 */
public class HessianTest {
    public static <T> byte[] serialize(T t) {
        byte[] data;
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            Hessian2Output output = new Hessian2Output(outputStream);
            output.writeObject(t);
            output.getBytesOutputStream().flush();
            output.completeMessage();
            output.close();
            data = outputStream.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return data;
    }

    public static <T> byte[] jdkSerialize(T t) {
        byte[] data;
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ObjectOutputStream output = new ObjectOutputStream(outputStream);
            output.writeObject(t);
            output.flush();
            output.close();
            data = outputStream.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return data;
    }

    public static void main(String[] args) {
        Demo demo = new Demo();
        demo.setName("2134");
        demo.setAge(14);
        byte[] serialize = serialize(demo);
        System.err.println("hessian序列化长度：" + serialize.length);
        byte[] jdkSerialize = jdkSerialize(demo);
        System.err.println("jdk序列化长度：" + jdkSerialize.length);
    }
}
