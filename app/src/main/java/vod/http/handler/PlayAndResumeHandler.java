package vod.http.handler;

import fi.iki.elonen.NanoHTTPD;
import fordream.http.RequestHandler;
import uk.co.caprica.vlcj.component.EmbeddedMediaListPlayerComponent;
import vod.player.DreamPlayListHelper;
import vod.util.AppProperty;
import vod.util.I18nHelper;
import vod.util.YoutubedlHelper;

import java.util.Base64;
import java.util.Map;

import static fi.iki.elonen.NanoHTTPD.newFixedLengthResponse;

/**
 * Handler of request to play (maybe resume)
 */
public class PlayAndResumeHandler implements RequestHandler {

    @Override
    public boolean doHandler(Map<String, String> args, String uri) {
        String action = args.get("action");
        return "play".equals(action);
    }

    @Override
    public NanoHTTPD.Response onRequest(String root, Map<String, String> args, NanoHTTPD.IHTTPSession session) {
        String url = args.get("url");
        String title = args.get("videoTitle");

        if (url == null) {
            // resume
            AppProperty.getSpringContext().getBean(EmbeddedMediaListPlayerComponent.class).getMediaListPlayer().play();
            return newFixedLengthResponse(I18nHelper.getString(this, "playSuccess"));
        } else {
            DreamPlayListHelper playListHelper = AppProperty.getSpringContext().getBean(DreamPlayListHelper.class);
            // play new item
            String selfUrl = AppProperty.get(AppProperty.P_KEY_WEBACCESS);

            int playlistCount = YoutubedlHelper.getPlaylistCount(url);

            EmbeddedMediaListPlayerComponent player = AppProperty.getSpringContext().
                    getBean(EmbeddedMediaListPlayerComponent.class);

            //int itemsCount = player.getMediaListPlayer().getMediaList().size();
            int itemsCount = playListHelper.size();

            if (itemsCount == 1) {
                if (player.getMediaListPlayer().getMediaList().items().get(0).mrl().indexOf("qrcode") > 0) {
                    itemsCount = -1;
                    player.getMediaListPlayer().stop();
                    player.getMediaListPlayer().getMediaList().clear();
                }
            }
            if (playlistCount > 0) {
                for (int i = 0; i < playlistCount; i++) {
                    String localPlayUrl = String.format("%s?action=transVlc&returnUrl=false&block=%d&url=%s",
                            selfUrl, i + 1, Base64.getEncoder().encodeToString(url.getBytes()));
                    playListHelper.add(localPlayUrl).setTitle(title);
                }
            }

            if (itemsCount < 0) {
                player.getMediaListPlayer().play();
                if (!AppProperty.isDebug())
                    player.getMediaPlayer().toggleFullScreen();
            }

            return newFixedLengthResponse(I18nHelper.getString(this, "addSuccess"));
        }
    }
}
