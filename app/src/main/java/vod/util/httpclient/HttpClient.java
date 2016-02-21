package vod.util.httpclient;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

/**
 * Created by forDream on 2015-12-27.
 */
public class HttpClient {
    static {
        defaultHandler = new HttpClientHandler() {
            @Override
            public void before(HttpURLConnection connection) {

            }

            @Override
            public void after(HttpURLConnection connection) {

            }
        };
    }

    private static HttpClientHandler defaultHandler;

    public static String getString(String url, Map<String, String> headers) {
        InputStream is = getStream(url, headers, null);
        if (is != null) {
            StringBuilder builder = new StringBuilder();
            byte[] buffer = new byte[4096];
            int len;
            try {
                while (-1 != (len = is.read(buffer, 0, buffer.length)))
                    builder.append(new String(buffer, 0, len));
            } catch (IOException e) {
                e.printStackTrace();
            }
            return builder.toString();
        }
        return null;
    }

    public static InputStream getStream(String url, Map<String, String> headers, HttpClientHandler handler) {
        try {
            URL u = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) u.openConnection();
            // set headers
            for (String key : headers.keySet())
                connection.setRequestProperty(key, headers.get(key));

            connection.setUseCaches(true);
            connection.setConnectTimeout(10000); // 10s

            if (handler == null) {
                defaultHandler.before(connection);
                connection.connect();
                defaultHandler.after(connection);
            } else {
                handler.before(connection);
                connection.connect();
                handler.after(connection);
            }
            return connection.getInputStream();
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Map<String, List<String>> head(String url, Map<String, String> headers) {
        try {
            URL u = new URL(url);

            HttpURLConnection connection = (HttpURLConnection) u.openConnection();
            // set headers
            for (String key : headers.keySet())
                connection.setRequestProperty(key, headers.get(key));

            connection.setRequestMethod("HEAD");

            connection.connect();

            return connection.getHeaderFields();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
