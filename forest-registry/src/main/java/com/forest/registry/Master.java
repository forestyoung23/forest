// package com.forest.registry;
//
// import org.apache.zookeeper.*;
//
// import java.io.IOException;
// import java.util.Random;
//
// /**
//  * @author Forest Dong
//  * @date 2022年08月21日 13:33
//  */
// public class Master implements Watcher {
//
//     static String hostPort = "localhost:2181";
//     static ZooKeeper zk = new ZooKeeper(hostPort, 15000, this);
//     static Random random = new Random();
//     static String serverId = Integer.toHexString(random.nextInt());
//     static boolean isLeader;
//
//
//     static void runForMaster() {
//         zk.create("/master", serverId.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL, masterCreateCallback, null);
//     }
//
//     static void startZk() throws IOException {
//         zk = new ZooKeeper(hostPort, 15000, this);
//     }
//
//     static void stopZK() throws Exception {
//         zk.close();
//     }
//
//
//     static void checkMaster() {
//         zk.getData("/master", false, masterCheckCallback, null);
//     }
//
//     static AsyncCallback.DataCallback masterCheckCallback = (rc, path, ctx, data, stat) -> {
//         switch (KeeperException.Code.get(rc)) {
//             case CONNECTIONLOSS:
//                 checkMaster();
//                 return;
//             case NONODE:
//                 runForMaster();
//                 return;
//         }
//     };
//     static AsyncCallback.StringCallback masterCreateCallback = new AsyncCallback.StringCallback() {
//         @Override
//         public void processResult(int rc, String path, Object ctx, String name) {
//             switch (KeeperException.Code.get(rc)) {
//                 case CONNECTIONLOSS:
//                     checkMaster();
//                     return;
//                 case OK:
//                     isLeader = true;
//                     break;
//                 default:
//                     isLeader = false;
//             }
//             System.err.println("I'm" + (isLeader ? " " : "not") + "the leader");
//         }
//     };
//
//     @Override
//     public void process(WatchedEvent event) {
//         System.err.println(event);
//     }
//
//     public void bootstrap() {
//         createParent("/workers", new byte[0]);
//         createParent("/assign", new byte[0]);
//         createParent("/tasks", new byte[0]);
//         createParent("/status", new byte[0]);
//     }
//
//     void createParent(String path, byte[] data) {
//         zk.create(path, data, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT, createParentCallback, data);
//     }
//
//     AsyncCallback.StringCallback createParentCallback = new AsyncCallback.StringCallback() {
//         @Override
//         public void processResult(int rc, String path, Object ctx, String name) {
//             switch (KeeperException.Code.get(rc)) {
//                 case CONNECTIONLOSS:
//                     createParent(path, (byte[]) ctx);
//                     break;
//                 case OK:
//                     System.err.println("Parent created");
//                     break;
//                 case NODEEXISTS:
//                     System.err.println("Parent already registered: " + path);
//                     break;
//                 default:
//                     System.err.println("Something went wrong: " + KeeperException.create(KeeperException.Code.get(rc), path));
//             }
//         }
//     };
//
//     public static void main(String[] args) throws Exception {
//         Master.startZk();
//         Master.runForMaster();
//         if (isLeader) {
//             System.err.println("I'm the leader");
//             Thread.sleep(60000);
//         } else {
//             System.err.println("Someone else is the leader");
//         }
//         Master.stopZK();
//     }
// }
