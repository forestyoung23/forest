package com.forest.ser.json;

import com.alibaba.fastjson.JSON;
import com.forest.ser.Encoder;

/**
 * @author Forest Dong
 * @date 2022年06月30日 23:07
 */
public class JsonEncoder implements Encoder {
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
        return JSON.toJSONBytes(obj);
    }
}
