package com.forest.transport;

import com.forest.protocol.Peer;

import java.io.InputStream;

/**
 * 1.创建连接
 * 2.发送数据，并等待响应
 * 3.关闭连接
 * @author Forest Dong
 * @date 2022年06月30日 23:31
 */
public interface TransportClient {
    /**
     * 创建连接
     *
     * @param peer
     * @return
     * @author Forest Dong
     * @date 2022/6/30 下午11:36
     */
    void connect(Peer peer);

    /**
     * 发送数据并等待响应
     *
     * @param data
     * @return
     * @author Forest Dong
     * @date 2022/6/30 下午11:36
     */
    InputStream write(InputStream data);

    /**
     * 关闭连接
     *
     * @param
     * @return
     * @author Forest Dong
     * @date 2022/6/30 下午11:36
     */
    void close();
}
