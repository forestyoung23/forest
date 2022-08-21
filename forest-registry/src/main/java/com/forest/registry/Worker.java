package com.forest.registry;

import org.apache.zookeeper.*;
import org.apache.zookeeper.common.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Random;

/**
 * @author Forest Dong
 * @date 2022年08月21日 14:05
 */
public class Worker implements Watcher {
    private static final Logger LOG = LoggerFactory.getLogger(Worker.class);
    ZooKeeper zk;
    String hostPort;
    Random random = new Random();
    String serverId = Integer.toHexString(random.nextInt());
    String name;

    String status;

    public void setStatus(String status) {
        this.status = status;
        updateStatus(status);
    }

    public String getName() {
        if (!StringUtils.isBlank(this.name)) {
            return this.name;
        }
        return "worker-" + serverId;
    }

    private void updateStatus(String status) {
        if (status.equals(this.status)) {
            zk.setData("/workers/" + getName(), status.getBytes(), -1, statusUpdateCallback, status);
        }
    }

    AsyncCallback.StatCallback statusUpdateCallback = (rc, path, ctx, stat) -> {
        switch (KeeperException.Code.get(rc)) {
            case CONNECTIONLOSS:
                updateStatus((String) ctx);
                return;
            default:
                return;
        }
    };

    Worker(String hostPort) {
        this.hostPort = hostPort;
    }

    @Override
    public void process(WatchedEvent event) {
        LOG.info(event.toString() + ", " + hostPort);
    }

    void startZK() throws IOException {
        zk = new ZooKeeper(hostPort, 15000, this);
    }

    void stopZK() throws InterruptedException {
        zk.close();
    }

    void register() {
        zk.create("/workers/" + getName(), "Idle".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL, createWorkerCallback, null);
    }

    AsyncCallback.StringCallback createWorkerCallback = (rc, path, ctx, name) -> {
        switch (KeeperException.Code.get(rc)) {
            case CONNECTIONLOSS:
                register();
                return;
            case OK:
                LOG.info("注册成功: " + serverId);
                break;
            case NODEEXISTS:
                LOG.error("该节点已注册: " + serverId);
                break;
            default:
                LOG.error("Something went wrong: " + KeeperException.create(KeeperException.Code.get(rc), path));
        }
    };

    public static void main(String[] args) throws Exception {
        Worker worker = new Worker("localhost:2181");
        worker.startZK();
        worker.register();
        Thread.sleep(30000);
    }
}
