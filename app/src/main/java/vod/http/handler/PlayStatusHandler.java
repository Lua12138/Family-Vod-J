package vod.http.handler;

import fi.iki.elonen.NanoHTTPD;
import fordream.http.RequestHandler;
import uk.co.caprica.vlcj.component.EmbeddedMediaListPlayerComponent;
import vod.util.AppProperty;
import vod.util.I18nHelper;

import java.util.Map;

import static fi.iki.elonen.NanoHTTPD.newFixedLengthResponse;

/**
 * Handler of handle player's play status.
 */
public class PlayStatusHandler implements RequestHandler {

    @Override
    public boolean doHandler(Map<String, String> args, String uri) {
        String action = args.get("action");
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

        return newFixedLengthResponse(I18nHelper.getString(this, "success"));
    }
}
