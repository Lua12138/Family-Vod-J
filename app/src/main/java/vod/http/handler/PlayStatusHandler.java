package vod.http.handler;

import fi.iki.elonen.NanoHTTPD;
import uk.co.caprica.vlcj.component.EmbeddedMediaListPlayerComponent;
import vod.http.RequestHandler;
import vod.util.AppProperty;

import java.util.Map;

import static fi.iki.elonen.NanoHTTPD.newFixedLengthResponse;

/**
 * Created by forDream on 2015-12-27.
 */
public class PlayStatusHandler implements RequestHandler {

    @Override
    public boolean doHandler(String action, String uri) {
        return "pause".equals(action) || "stop".equals(action);
    }

    @Override
    public NanoHTTPD.Response onRequest(String root, Map<String, String> args, NanoHTTPD.IHTTPSession session) {
        String action = args.get("action");
        if ("stop".equals(action)) {
            AppProperty.getSpringContext().getBean(EmbeddedMediaListPlayerComponent.class).getMediaListPlayer().stop();
        } else {
            AppProperty.getSpringContext().getBean(EmbeddedMediaListPlayerComponent.class).getMediaListPlayer().pause();
        }

        return newFixedLengthResponse("操作成功");
    }
}
