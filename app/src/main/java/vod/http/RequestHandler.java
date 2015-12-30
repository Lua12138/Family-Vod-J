package vod.http;

import java.util.Map;

import static fi.iki.elonen.NanoHTTPD.IHTTPSession;
import static fi.iki.elonen.NanoHTTPD.Response;

public interface RequestHandler {
    /**
     * @return return true, if this handler handle the request.
     */
    boolean doHandler(String action, String uri);

    Response onRequest(String root, Map<String, String> args, IHTTPSession session);
}
