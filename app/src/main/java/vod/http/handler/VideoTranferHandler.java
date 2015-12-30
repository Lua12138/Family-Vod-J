package vod.http.handler;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.javafx.collections.MappingChange;
import fi.iki.elonen.NanoHTTPD;
import vod.http.RequestHandler;
import vod.util.AppProperty;
import vod.util.YoutubedlHelper;
import vod.util.httpclient.HttpClient;
import vod.util.httpclient.HttpClientHandler;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.*;

import static fi.iki.elonen.NanoHTTPD.*;

/**
 * Created by forDream on 2015-12-27.
 */
public class VideoTranferHandler implements RequestHandler {

    @Override
    public boolean doHandler(String action, String uri) {
        return "vlc".equals(action) || "transVlc".equals(action);
    }

    @Override
    public Response onRequest(String root, Map<String, String> args, NanoHTTPD.IHTTPSession session) {
        Response response;
        String action = args.get("action");

        if ("transVlc".equals(action)) {
            // step 1 ->
            String url = args.get("url");
            url = new String(Base64.getDecoder().decode(url.getBytes()));
            int index = Integer.parseInt(args.get("block"));

            String json = YoutubedlHelper.getVideoInfo(index, url);
            if (json.startsWith("{")) {
                response = newFixedLengthResponse(Response.Status.REDIRECT, MIME_PLAINTEXT, null); // 301
                // 301 -> ?action=vlc&url=base64(json)
                response.addHeader("Location", String.format("%s?action=vlc&base64=%s",
                        AppProperty.get(AppProperty.P_KEY_WEBACCESS),
                        Base64.getEncoder().encodeToString(json.getBytes())
                ));

            } else {
                response = newFixedLengthResponse(Response.Status.INTERNAL_ERROR, MIME_PLAINTEXT, null); //500
            }
        } else {
            // step 2 ->
            String base64 = args.get("base64");
            String json = new String(Base64.getDecoder().decode(base64));
            ObjectMapper mapper = new ObjectMapper();
            //response = null; // <- delete it
            try {
                JsonNode node = mapper.readTree(json);
                String url = node.get("url").asText();
                JsonNode headers = node.get("http_headers");

                Map<String, String> requestHeader = new HashMap<>();

                for (Iterator<String> iterator = headers.fieldNames(); iterator.hasNext(); ) {
                    String key = iterator.next();
                    requestHeader.put(key, headers.get(key).asText());
                }

                // query file size
                Map<String, List<String>> remoteHeader = HttpClient.head(url, requestHeader);
                long size = Long.parseLong(remoteHeader.get("Content-Length").get(0));

                InputStream is = HttpClient.getStream(url, requestHeader, null);
                response = newChunkedResponse(Response.Status.OK, remoteHeader.get("Content-Type").get(0), is);
            } catch (IOException e) {
                e.printStackTrace();
                response = null;
            }
        }
        return response;
    }
}
