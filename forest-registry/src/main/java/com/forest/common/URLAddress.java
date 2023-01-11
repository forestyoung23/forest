package com.forest.common;

/**
 * @author Forest Dong
 * @date 2022年08月28日 15:37
 */
public class URLAddress {
    private String host;
    private int port;

    public URLAddress(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
