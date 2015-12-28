package vod.http;

import java.util.Map;
import static fi.iki.elonen.NanoHTTPD.IHTTPSession;
import static fi.iki.elonen.NanoHTTPD.Response;

public interface RequestHandler {
    /**
     * @return response action string
     */
    String getResponseAction();

    Response onRequest(String root, Map<String, String> args, IHTTPSession session);
}
