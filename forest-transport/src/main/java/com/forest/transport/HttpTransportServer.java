package com.forest.transport;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author Forest Dong
 * @date 2022年06月30日 23:53
 */
@Slf4j
public class HttpTransportServer implements TransportServer {
    private RequestHandler handler;
    private Server server;

    /**
     * 初始化
     *
     * @param port
     * @param handler
     * @return
     * @author Forest Dong
     * @date 2022/7/1 上午12:03
     */
    @Override
    public void init(int port, RequestHandler handler) {
        this.handler = handler;
        this.server = new Server(port);
        ServletContextHandler contextHandler = new ServletContextHandler();
        server.setHandler(contextHandler);
        ServletHolder holder = new ServletHolder(new RequestServlet());
        contextHandler.addServlet(holder, "/*");
    }

    /**
     * 启动服务并监听
     *
     * @return
     * @author Forest Dong
     * @date 2022/6/30 下午11:37
     */
    @Override
    public void start() {
        try {
            server.start();
            server.join();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * 停止服务
     *
     * @param
     * @return
     * @author Forest Dong
     * @date 2022/7/1 上午12:03
     */
    @Override
    public void stop() {
        try {
            server.stop();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    class RequestServlet extends HttpServlet {
        @Override
        protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            log.info("client connect");
            InputStream in = req.getInputStream();
            OutputStream out = resp.getOutputStream();
            if (handler != null) {
                handler.onRequest(in, out);
            }
            out.flush();
        }
    }
}
