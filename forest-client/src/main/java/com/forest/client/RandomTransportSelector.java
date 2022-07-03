package com.forest.client;

import com.forest.common.ReflectionUtils;
import com.forest.protocol.Peer;
import com.forest.transport.TransportClient;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Forest Dong
 * @date 2022年07月03日 16:17
 */
@Slf4j
public class RandomTransportSelector implements TransportSelector {

    private List<TransportClient> clients;

    public RandomTransportSelector() {
        this.clients = new ArrayList<>();
    }

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
    @Override
    public synchronized void init(List<Peer> peers, int count, Class<? extends TransportClient> clazz) {
        count = Math.max(count, 1);
        for (Peer peer : peers) {
            for (int i = 0; i < count; i++) {
                TransportClient client = ReflectionUtils.newInstance(clazz);
                client.connect(peer);
                clients.add(client);
            }
        }
    }

    /**
     * 选择一个transport与server做交互
     *
     * @return
     * @author Forest Dong
     * @date 2022/7/3 下午4:15
     */
    @Override
    public synchronized TransportClient select() {
        int i = new Random().nextInt(clients.size());
        return clients.remove(i);
    }

    /**
     * 释放用完的client
     *
     * @param client
     * @return
     * @author Forest Dong
     * @date 2022/7/3 下午4:16
     */
    @Override
    public synchronized void release(TransportClient client) {
        clients.add(client);
    }

    /**
     * 关闭连接
     *
     * @return
     * @author Forest Dong
     * @date 2022/7/3 下午4:16
     */
    @Override
    public synchronized void close() {
        for (TransportClient client : clients) {
            client.close();
        }
        clients.clear();
    }
}
