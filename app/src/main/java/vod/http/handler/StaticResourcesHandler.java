package vod.http.handler;

import fi.iki.elonen.NanoHTTPD;
import vod.http.MimeHelper;
import vod.http.RequestHandler;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

import static fi.iki.elonen.NanoHTTPD.MIME_PLAINTEXT;
import static fi.iki.elonen.NanoHTTPD.newFixedLengthResponse;

/**
 * Created by forDream on 2015-12-26.
 */
public class StaticResourcesHandler implements RequestHandler {
    @Override
    public String getResponseAction() {
        return null;
    }

    @Override
    public NanoHTTPD.Response onRequest(String root, Map<String, String> args, NanoHTTPD.IHTTPSession session) {
        if (args.size() > 0)
            ;
        else {
            String path;
            if ("/".equals(session.getUri()))
                path = root + "index.html";
            else
                path = root + session.getUri();
            File file = new File(path);
            if (file.exists()) {
                try {
                    FileInputStream is = new FileInputStream(file);
                    // nano httpd will close the stream
                    return newFixedLengthResponse(NanoHTTPD.Response.Status.OK,
                            MimeHelper.getMime(file.getAbsolutePath()), is, file.length());
                } catch (FileNotFoundException e) { // checked
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                // http code 404
                return newFixedLengthResponse(NanoHTTPD.Response.Status.NOT_FOUND, MIME_PLAINTEXT, null);
            }
        }
        return null;
    }
}
