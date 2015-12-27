package vod.http;

import fi.iki.elonen.NanoHTTPD;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Http Server
 */
public final class DreamHttp extends NanoHTTPD {
    private List<RequestHandler> handlers;

    private String webRoot; // root path

    public DreamHttp(String root) {
        super(new Random().nextInt(55535) + 10000);
        this.handlers = new ArrayList<>();
        if (!root.endsWith("/"))
            root = root + "/";
        this.webRoot = root;
    }

    /**
     * register new http request handler
     *
     * @param handler
     */
    public void registerHandler(RequestHandler handler) {
        boolean repeat = false;
        for (RequestHandler h : this.handlers) {
            if (h.getResponseAction().equals(handler.getResponseAction())) {
                repeat = true;
                break;
            }
        }

        // add to list
        if (!repeat)
            this.handlers.add(handler);
    }

    /**
     * remove the registered request handler
     *
     * @param handler
     */
    public void removeHandler(RequestHandler handler) {
        for (int i = 0; i < this.handlers.size(); i++) {
            if (this.handlers.get(i).getResponseAction().equals(handler.getResponseAction())) {
                this.handlers.remove(i);
                break;
            }
        }
    }

    @Override
    public Response serve(IHTTPSession session) {
        Map<String, String> args = session.getParms();
        String action = args.get("action");

        Response response = null;
        for (RequestHandler handler : this.handlers) {
            if (handler.getResponseAction() == action ||
                    handler.getResponseAction().equals(action)) {
                response = handler.onRequest(this.webRoot, args, session);
                break;
            }
        }

        if (response == null)
            // return no content ,if no handler.
            return newFixedLengthResponse(Response.Status.NO_CONTENT, NanoHTTPD.MIME_PLAINTEXT, null);
        else
            return response;
    }
}
