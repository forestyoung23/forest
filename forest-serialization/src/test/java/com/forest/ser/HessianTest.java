package com.forest.ser;

import com.caucho.hessian.io.Hessian2Output;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Output;

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

    public static <T> byte[] kryoSerialize(T t) {
        Kryo kryo = new Kryo();
        //使用注册模式序列化后数据体积更小，速度更快
        // kryo.register(t.getClass());
        // 如未注册则必须配置这项，不然会报错：Class is not registered
        kryo.setRegistrationRequired(false);
        try (Output output = new Output(128, -1)) {
            kryo.writeClassAndObject(output, t);
            return output.toBytes();
        }
    }

    public static <T> byte[] kryoRegistSerialize(T t) {
        Kryo kryo = new Kryo();
        //使用注册模式序列化后数据体积更小，速度更快
        kryo.register(t.getClass());
        // 如未注册则必须配置这项，不然会报错：Class is not registered
        // kryo.setRegistrationRequired(false);
        try (Output output = new Output(128, -1)) {
            kryo.writeClassAndObject(output, t);
            return output.toBytes();
        }
    }

    public static void main(String[] args) {
        Demo demo = new Demo();
        demo.setName("2134");
        demo.setAge(14);
        byte[] serialize = serialize(demo);
        System.err.println("hessian序列化长度：" + serialize.length);
        byte[] jdkSerialize = jdkSerialize(demo);
        System.err.println("jdk序列化长度：" + jdkSerialize.length);
        byte[] kryoSerialize = kryoSerialize(demo);
        System.err.println("kryo序列化长度：" + kryoSerialize.length);
        byte[] kryoRegistSerialize = kryoRegistSerialize(demo);
        System.err.println("kryo注册模式序列化长度：" + kryoRegistSerialize.length);
    }
}
