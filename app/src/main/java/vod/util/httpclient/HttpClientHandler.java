package vod.util.httpclient;

import java.net.HttpURLConnection;

/**
 * Created by forDream on 2015-12-27.
 */
public interface HttpClientHandler {
    void before(HttpURLConnection connection);

    void after(HttpURLConnection connection);
}