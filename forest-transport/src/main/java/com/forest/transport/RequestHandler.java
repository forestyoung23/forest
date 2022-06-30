package com.forest.transport;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * 请求处理器
 *
 * @author Forest Dong
 * @date 2022年06月30日 23:38
 */
public interface RequestHandler {
    /**
     * 请求处理
     *
     * @param is
     * @param os
     * @return
     * @author Forest Dong
     * @date 2022/7/1 上午12:02
     */
    void onRequest(InputStream is, OutputStream os);
}
