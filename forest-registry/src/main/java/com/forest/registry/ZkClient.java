// package com.forest.registry;
//
// import org.apache.zookeeper.*;
// import org.apache.zookeeper.data.Stat;
//
// import java.io.IOException;
// import java.util.Random;
//
// /**
//  * @author Forest Dong
//  * @date 2022年08月14日 15:09
//  */
// public class ZkClient implements Watcher {
//
//     ZooKeeper zk;
//     String hostPort;
//     Random random = new Random();
//     String serverId = Integer.toHexString(random.nextInt());
//     static boolean isLeader = false;
//
//     ZkClient(String hostPort) {
//         this.hostPort = hostPort;
//     }
//
//     boolean checkMaster() {
//         while (true) {
//             try {
//                 Stat stat = new Stat();
//                 byte[] data = zk.getData("/master", false, stat);
//                 isLeader = new String(data).equals(serverId);
//                 return true;
//             } catch (KeeperException | InterruptedException e) {
//                 return false;
//             }
//         }
//     }
//
//     void runForMaster() throws InterruptedException {
//         while (true) {
//             try {
//                 System.currentTimeMillis()
//                 zk.create("/master", serverId.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
//                 isLeader = true;
//                 break;
//             } catch (KeeperException.NodeExistsException e) {
//                 isLeader = false;
//                 break;
//             } catch (KeeperException e) {
//             }
//             if (checkMaster()) {
//                 break;
//             }
//         }
//     }
//
//     void startZk() throws IOException {
//         zk = new ZooKeeper(hostPort, 15000, this);
//     }
//
//     void stopZK() throws Exception {
//         zk.close();
//     }
//
//     @Override
//     public void process(WatchedEvent event) {
//         System.err.println(event);
//     }
//
//     public static void main(String[] args) throws Exception {
//         ZkClient client = new ZkClient("localhost:2181");
//         client.startZk();
//         client.runForMaster();
//         if (isLeader) {
//             System.err.println("I'm the leader");
//             Thread.sleep(60000);
//         } else {
//             System.err.println("Someone else is the leader");
//         }
//         client.stopZK();
//     }
// }