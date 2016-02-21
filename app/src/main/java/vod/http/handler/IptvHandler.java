package vod.http.handler;

import fi.iki.elonen.NanoHTTPD;
import fordream.http.RequestHandler;
import vod.player.DreamPlayListHelper;
import vod.util.AppProperty;
import vod.util.I18nHelper;
import vod.util.httpclient.HttpClient;

import java.util.HashMap;
import java.util.Map;

import static fi.iki.elonen.NanoHTTPD.Response.Status;
import static fi.iki.elonen.NanoHTTPD.newFixedLengthResponse;

/**
 * Handler of show iptv list
 */
public class IptvHandler implements RequestHandler {

    @Override
    public boolean doHandler(Map<String, String> args, String uri) {
        String action = args.get("action");
        return "iptv".equals(action) || "returnIPTV".equals(action);
    }

    @Override
    public NanoHTTPD.Response onRequest(String root, Map<String, String> args, NanoHTTPD.IHTTPSession session) {
        String action = args.get("action");
        String url = args.get("url");
        DreamPlayListHelper helper = AppProperty.getSpringContext().getBean(DreamPlayListHelper.class);

        if ("iptv".equals(action)) {
            // test url
            Map map = HttpClient.head(url, new HashMap<>());
            if (map.size() > 0) {
                helper.playIPTV(url);
                return newFixedLengthResponse(Status.OK, "text/plain", I18nHelper.getString(this, "success"));
            } else {
                return newFixedLengthResponse(Status.OK, "text/plain", I18nHelper.getString(this, "fail"));
            }
        } else {
            helper.resumeVod();
            return newFixedLengthResponse(Status.OK, "text/plain", I18nHelper.getString(this, "success"));
        }
    }
}
