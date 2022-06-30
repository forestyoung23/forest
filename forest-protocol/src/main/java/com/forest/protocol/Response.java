package com.forest.protocol;

import lombok.Data;

/**
 * 表示返回
 *
 * @author Forest Dong
 * @date 2022年06月30日 22:26
 */
@Data
public class Response {
    /**
     * 状态码
     */
    private int code;
    /**
     * 状态信息
     */
    private String message;

    /**
     * 返回数据
     */
    private Object data;
}
