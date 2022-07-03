package com.forest.client;

import com.forest.protocol.Peer;
import com.forest.transport.TransportClient;

import java.util.List;

/**
 * Server选择器
 *
 * @author Forest Dong
 * @date 2022年07月03日 16:05
 */
public interface TransportSelector {
    /**
     * 初始化selector
     *
     * @param peers 可以连接的server端点信息
     * @param count client与server建立多少个连接
     * @param clazz client实现class
     * @return
     * @author Forest Dong
     * @date 2022/7/3 下午4:15
     */
    void init(List<Peer> peers, int count, Class<? extends TransportClient> clazz);

    /**
     * 选择一个transport与server做交互
     *
     * @return
     * @author Forest Dong
     * @date 2022/7/3 下午4:15
     */
    TransportClient select();

    /**
     * 释放用完的client
     *
     * @param client
     * @return
     * @author Forest Dong
     * @date 2022/7/3 下午4:16
     */
    void release(TransportClient client);

    /**
     * 关闭连接
     *
     * @return
     * @author Forest Dong
     * @date 2022/7/3 下午4:16
     */
    void close();
}
