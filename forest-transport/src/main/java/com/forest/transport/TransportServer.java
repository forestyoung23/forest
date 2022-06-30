package com.forest.transport;

/**
 * @author Forest Dong
 * @date 2022年06月30日 23:35
 */
public interface TransportServer {
    /**
     * 初始化
     *
     * @param port
     * @param handler
     * @return
     * @author Forest Dong
     * @date 2022/7/1 上午12:03
     */
    void init(int port, RequestHandler handler);

    /**
     * 启动服务并监听
     *
     * @param
     * @return
     * @author Forest Dong
     * @date 2022/6/30 下午11:37
     */
    void start();

    /**
     * 停止服务
     *
     * @param
     * @return
     * @author Forest Dong
     * @date 2022/7/1 上午12:03
     */
    void stop();
}
