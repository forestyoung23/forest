package com.forest.ser.json;

import com.alibaba.fastjson.JSON;
import com.forest.ser.Decoder;

/**
 * @author Forest Dong
 * @date 2022年06月30日 23:08
 */
public class JsonDecoder implements Decoder {
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
        return JSON.parseObject(bytes, clazz);
    }
}
