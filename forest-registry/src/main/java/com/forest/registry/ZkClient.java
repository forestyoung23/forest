package com.forest.registry;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;

/**
 * @author Forest Dong
 * @date 2022年08月14日 15:09
 */
public class ZkClient implements Watcher {

    ZooKeeper zk;
    String hostPort;

    ZkClient(String hostPort) {
        this.hostPort = hostPort;
    }

    void startZk() throws IOException {
        zk = new ZooKeeper(hostPort, 15000, this);
    }
    @Override
    public void process(WatchedEvent event) {
        System.err.println(event);
    }

    public static void main(String[] args) throws Exception {
        ZkClient client = new ZkClient("localhost:2181");
        client.startZk();
        Thread.sleep(60000);
    }
}
