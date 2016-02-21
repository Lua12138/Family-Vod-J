package vod.http.handler;

import fi.iki.elonen.NanoHTTPD;
import fordream.http.RequestHandler;
import vod.util.httpclient.HttpClient;
import vod.util.httpclient.HttpClientHandler;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static fi.iki.elonen.NanoHTTPD.Response;
import static fi.iki.elonen.NanoHTTPD.newFixedLengthResponse;

/**
 * Handler of show image (in fact, it is a proxy)
 */
public class ImageTransferHandler implements RequestHandler {

    @Override
    public boolean doHandler(Map<String, String> args, String uri) {
        return "/services/browser/img".equals(uri);
    }

    @Override
    public Response onRequest(String root, Map<String, String> args, NanoHTTPD.IHTTPSession session) {
        String url = args.get("url");
        final Map<String, List<String>>[] responseHeaders = new Map[1];

        InputStream stream = HttpClient.getStream(url, new HashMap<>(), new HttpClientHandler() {
            @Override
            public void before(HttpURLConnection connection) {

            }

            @Override
            public void after(HttpURLConnection connection) {
                responseHeaders[0] = connection.getHeaderFields();
            }
        });

        Response response = null;

        if (null != stream) {
            response = newFixedLengthResponse(
                    Response.Status.OK, responseHeaders[0].get("Content-Type").get(0), stream,
                    Long.parseLong(responseHeaders[0].get("Content-Length").get(0)));
        }
        return response;
    }
}
