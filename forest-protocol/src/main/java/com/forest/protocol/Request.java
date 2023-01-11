package com.forest.protocol;

import lombok.Data;

import java.io.Serializable;

/**
 * 请求
 *
 * @author Forest Dong
 * @date 2022年06月30日 22:25
 */
@Data
public class Request implements Serializable {
    /**
     * 请求服务
     */
    private ServiceDesc service;
    /**
     * 请求参数
     */
    private Object[] params;
}
