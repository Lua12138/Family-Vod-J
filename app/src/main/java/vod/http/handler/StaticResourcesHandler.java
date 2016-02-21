package vod.http.handler;

import fi.iki.elonen.NanoHTTPD;
import fordream.http.MimeHelper;
import fordream.http.RequestHandler;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Map;

import static fi.iki.elonen.NanoHTTPD.MIME_PLAINTEXT;
import static fi.iki.elonen.NanoHTTPD.newFixedLengthResponse;

/**
 * Handler of Static Resources
 */
public class StaticResourcesHandler implements RequestHandler {

    @Override
    public boolean doHandler(Map<String, String> args, String uri) {
        return args.get("action") == null;
    }

    @Override
    public NanoHTTPD.Response onRequest(String root, Map<String, String> args, NanoHTTPD.IHTTPSession session) {
        String path;
        if ("/".equals(session.getUri())) {
            String ua = session.getHeaders().get("user-agent");
            if (ua.indexOf("Mobile") > 0)
                path = root + "mobile.html";
            else
                path = root + "pc.html";
        } else
            path = root + session.getUri();
        File file = new File(path);
        if (file.exists()) {
            try {
                FileInputStream is = new FileInputStream(file);
                // nano httpd will close the stream
                return newFixedLengthResponse(NanoHTTPD.Response.Status.OK,
                        MimeHelper.instance().getMime(file.getAbsolutePath()), is, file.length());
            } catch (FileNotFoundException e) { // checked
                e.printStackTrace();
            }
        } else {
            // http code 404
            return newFixedLengthResponse(NanoHTTPD.Response.Status.NOT_FOUND, MIME_PLAINTEXT, null);
        }

        return null;
    }
}
