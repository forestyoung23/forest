package com.forest.example;

import com.forest.server.RpcServer;

/**
 * @author Forest Dong
 * @date 2022年07月03日 17:04
 */
public class Server {
    public static void main(String[] args) {
        RpcServer server = new RpcServer();
        server.register(ClacService.class, new ClacServiceImpl());
        server.start();
    }
}
