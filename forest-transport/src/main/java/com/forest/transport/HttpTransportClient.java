package com.forest.transport;

import com.forest.protocol.Peer;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author Forest Dong
 * @date 2022年06月30日 23:41
 */
public class HttpTransportClient implements TransportClient {
    private String url;
    /**
     * 创建连接
     *
     * @param peer
     * @return
     * @author Forest Dong
     * @date 2022/6/30 下午11:36
     */
    @Override
    public void connect(Peer peer) {
        this.url = "http://" + peer.getHost() + ":" + peer.getPort();
    }

    /**
     * 发送数据并等待响应
     *
     * @param data
     * @return
     * @author Forest Dong
     * @date 2022/6/30 下午11:36
     */
    @Override
    public InputStream write(InputStream data) {
        try {
            HttpURLConnection urlConnection = (HttpURLConnection)new URL(url).openConnection();
            // urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);
            // TODO: 2022/6/30 usecache作用？
            urlConnection.setUseCaches(false);
            urlConnection.setRequestMethod("post");
            urlConnection.connect();
            IOUtils.copy(data, urlConnection.getOutputStream());
            int responseCode = urlConnection.getResponseCode();
            if (HttpURLConnection.HTTP_OK == responseCode ) {
                return urlConnection.getInputStream();
            } else {
                return urlConnection.getErrorStream();
            }
        } catch (IOException e) {
           throw new IllegalStateException(e);
        }
    }

    /**
     * 关闭连接
     *
     * @return
     * @author Forest Dong
     * @date 2022/6/30 下午11:36
     */
    @Override
    public void close() {

    }
}
