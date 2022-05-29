package com.forest.registry;

import org.I0Itec.zkclient.ZkClient;

/**
 * @author Forest Dong
 * @date 2021年10月06日 17:29
 */
public class ZkRegistry implements ServiceRegistry {

    public static ZkClient zkClient;

    static {
        zkClient = new ZkClient("127.0.0.1:2181");
    }

    /**
     * 服务注册
     *
     * @return
     * @author Forest Dong
     * @date 2021/10/7 下午3:13
     */
    @Override
    public void registry() {
        zkClient.createPersistent("/");
        zkClient.createEphemeral("/forest");
    }

    public static void main(String[] args) {
        ZkRegistry zkRegistry = new ZkRegistry();
        zkRegistry.registry();
    }
}
