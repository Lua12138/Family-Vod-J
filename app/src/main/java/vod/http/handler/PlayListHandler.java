package vod.http.handler;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import fi.iki.elonen.NanoHTTPD;
import uk.co.caprica.vlcj.player.MediaMetaData;
import vod.http.RequestHandler;
import vod.player.DreamPlayListHelper;
import vod.util.AppProperty;

import java.io.*;
import java.util.Map;

import static fi.iki.elonen.NanoHTTPD.newFixedLengthResponse;

/**
 * Created by forDream on 2016-01-04.
 */
public class PlayListHandler implements RequestHandler {
    @Override
    public boolean doHandler(String action, String uri) {
        return "showList".equals(action);
    }

    @Override
    public NanoHTTPD.Response onRequest(String root, Map<String, String> args, NanoHTTPD.IHTTPSession session) {
        JsonFactory factory = new JsonFactory();
        OutputStream os = new ByteArrayOutputStream();
        try {
            DreamPlayListHelper playListHelper = AppProperty.getSpringContext().getBean(DreamPlayListHelper.class);

            JsonGenerator generator = factory.createGenerator(os);
            generator.writeStartArray();

            if (playListHelper.getVideoType() == DreamPlayListHelper.VideoType.VOD)
                for (int i = 0; i < playListHelper.size(); i++) {
                    MediaMetaData data = playListHelper.get(i);
                    generator.writeStartObject();
                    generator.writeNumberField("id", i + 1);
                    generator.writeStringField("title", data.getTitle());
                    generator.writeStringField("playstatus", data.getNowPlaying());
                    generator.writeEndObject();
                }
            else {

            }
            generator.writeEndArray();
            generator.close();

            InputStream is = new ByteArrayInputStream(((ByteArrayOutputStream) os).toByteArray());
            return newFixedLengthResponse(NanoHTTPD.Response.Status.OK, "application/json", is, ((ByteArrayOutputStream) os).size());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
