package com.forest.ser;

/**
 * 序列化接口
 *
 * @author Forest Dong
 * @date 2022年06月30日 23:01
 */
public interface Encoder {
    /**
     * 序列化
     *
     * @param obj
     * @return
     * @author Forest Dong
     * @date 2022/6/30 下午11:05
     */
    byte[] encode(Object obj);
}
