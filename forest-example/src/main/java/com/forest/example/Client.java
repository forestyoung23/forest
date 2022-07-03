package com.forest.example;

import com.forest.client.RpcClient;

/**
 * @author Forest Dong
 * @date 2022年07月03日 17:04
 */
public class Client {
    public static void main(String[] args) {
        RpcClient rpcClient = new RpcClient();
        ClacService proxy = rpcClient.getProxy(ClacService.class);
        int add = proxy.add(1, 2);
        int minus = proxy.minus(8, 2);
        System.err.println(add);
        System.err.println(minus);
    }
}
