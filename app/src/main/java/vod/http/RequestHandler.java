package vod.http;

import fi.iki.elonen.NanoHTTPD;

import java.util.Map;

public interface RequestHandler {
    /**
     * @return response action string
     */
    String getResponseAction();

    NanoHTTPD.Response onRequest(String root, Map<String, String> args, NanoHTTPD.IHTTPSession session);
}
