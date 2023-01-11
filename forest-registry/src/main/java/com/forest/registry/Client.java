package com.forest.registry;

import org.apache.zookeeper.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @author Forest Dong
 * @date 2022年08月21日 14:54
 */
public class Client implements Watcher {
    private static final Logger LOG = LoggerFactory.getLogger(Client.class);
    ZooKeeper zk;
    String hostPort;

    Client(String hostPort) {
        this.hostPort = hostPort;
    }

    void startZk() throws IOException {
        zk = new ZooKeeper(hostPort, 15000, this);
    }

    String queueCommand(String command) throws Exception {
        String name = "-";
        while (true) {
            try {
                name = zk.create("/tasks/task-", command.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT_SEQUENTIAL);
                return name;
            } catch (KeeperException.NodeExistsException e) {
                throw new Exception(name + " already appears to be running");
            } catch (KeeperException.ConnectionLossException e) {

            }
        }

    }

    @Override
    public void process(WatchedEvent event) {
        System.err.println(event);
    }

    public static void main(String[] args) throws Exception {
        Client client = new Client("localhost:2181");
        client.startZk();
        String motherfucker = client.queueCommand("motherfucker");
        System.err.println(motherfucker);

    }
}
