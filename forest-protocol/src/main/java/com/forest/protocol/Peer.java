package com.forest.protocol;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 网络端点
 *
 * @author Forest Dong
 * @date 2022年06月30日 22:20
 */
@Data
@AllArgsConstructor
public class Peer {
    /**
     * 主机
     */
    private String host;

    /**
     * 端口
     */
    private int port;
}
