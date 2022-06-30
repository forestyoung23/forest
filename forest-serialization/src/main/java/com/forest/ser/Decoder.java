package com.forest.ser;

/**
 * 反序列化接口
 *
 * @author Forest Dong
 * @date 2022年06月30日 23:03
 */
public interface Decoder {
    /**
     * 反序列化
     *
     * @param bytes
     * @param clazz
     * @return
     * @author Forest Dong
     * @date 2022/6/30 下午11:06
     */
    <T> T decode(byte[] bytes, Class<T> clazz);
}
