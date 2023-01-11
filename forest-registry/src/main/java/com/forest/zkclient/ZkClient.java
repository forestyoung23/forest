package com.forest.zkclient;

import org.apache.zookeeper.*;
import org.apache.zookeeper.client.ZKClientConfig;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.UUID;

/**
 * @author Forest Dong
 * @date 2022年08月21日 22:55
 */
public class ZkClient implements Watcher {
    private static final Logger LOG = LoggerFactory.getLogger(ZkClient.class);
    /**
     * 主节点
     */
    private static String NODE_MASTER = "/master";
    /**
     * 工作节点
     */
    private static String NODE_WORKERS = "/workers";

    /**
     * 任务节点
     */
    private static String NODE_TASKS = "/tasks";

    /**
     * 任务分配节点
     */
    private static String NODE_ASSIGN = "/assign";

    private String hostPort;
    private ZooKeeper zk;

    private boolean isLeader = false;

    public ZkClient(String hostPort) {
        this.hostPort = hostPort;
    }

    public void startZk() throws IOException {
        zk = new ZooKeeper(hostPort, 15000, this);
        // zk.create(NODE_MASTER, UUID.randomUUID().toString().getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        // zk.create(NODE_WORKERS, UUID.randomUUID().toString().getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        // zk.create(NODE_TASKS, UUID.randomUUID().toString().getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        // zk.create(NODE_ASSIGN, UUID.randomUUID().toString().getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
    }

    void runForMaster() {
        byte[] bytes = UUID.randomUUID().toString().getBytes();
        zk.create(NODE_MASTER, bytes, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL, createMasterCallback, bytes);
    }

    void masterExists() {
        // zk.exists("/master", );
    }

    AsyncCallback.StringCallback masterCreateCallback = (rc, path, ctx, name) -> {
        switch (KeeperException.Code.get(rc)) {
            case CONNECTIONLOSS:
                // checkMaster();
                break;
            case OK:
                LOG.info("当选主节点");
                isLeader = true;
                break;
            default:
                LOG.error("运行异常");
                isLeader = false;
        }
    };
    AsyncCallback.StringCallback createMasterCallback = (rc, path, ctx, name) -> {
        switch (KeeperException.Code.get(rc)) {
            case CONNECTIONLOSS:
                if (checkMaster(path, ctx)) {
                    LOG.info("当选主节点");
                    return;
                } else {
                    runForMaster();
                }
            case OK:
                LOG.info("当选主节点");
                isLeader = true;
                break;
            default:
                LOG.error("运行异常");
                isLeader = false;
        }
    };

    private boolean  checkMaster(String path, Object ctx) {
        while (true) {
            try {
                Stat stat = new Stat();
                byte[] data = zk.getData(path, false, stat);
                isLeader = new String(data).equals(new String((byte[]) ctx));
                return true;
            } catch (KeeperException.NoNodeException e) {
                return false;
            } catch (KeeperException.ConnectionLossException e){
                LOG.warn("连接丢失，再次尝试");
            } catch (KeeperException | InterruptedException e) {
            }
        }
    }

    public void stopZk() throws InterruptedException {
        zk.close();
    }

    @Override
    public void process(WatchedEvent event) {
        System.err.println("zookeeper启动成功");
    }



    public static void main(String[] args) throws InterruptedException, IOException {
        ZkClient zkClient = new ZkClient("localhost:2181");
        zkClient.startZk();
        zkClient.runForMaster();
        Thread.sleep(10000);
        zkClient.stopZk();
    }
}
