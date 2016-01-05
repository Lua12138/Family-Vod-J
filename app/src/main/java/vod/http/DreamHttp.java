package vod.http;

import fi.iki.elonen.NanoHTTPD;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vod.util.AppProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static fi.iki.elonen.NanoHTTPD.Response.Status;

/**
 * Http Server
 */
public final class DreamHttp extends NanoHTTPD {
    private List<RequestHandler> handlers;

    private String webRoot; // root path
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public DreamHttp(int port) {
        super(port);
        this.handlers = new ArrayList<>();
    }

    public void setWebRoot(String webRoot) {
        this.webRoot = webRoot;
    }

    /**
     * register new http request handler
     *
     * @param handler
     */
    public void registerHandler(RequestHandler handler) {
        boolean repeat = false;
        for (RequestHandler h : this.handlers) {
            if (h.getClass().equals(handler.getClass())) {
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
            if (this.handlers.get(i).getClass().equals(handler.getClass())) {
                this.handlers.remove(i);
                break;
            }
        }

    }

    @Override
    public Response serve(IHTTPSession session) {
        try {
            Map<String, String> args = session.getParms();
            String action = args.get("action");

            logger.debug("request uri -> {}, action -> {}", session.getUri(), action);
            Response response = null;
            for (RequestHandler handler : this.handlers) {
                if (handler.doHandler(action, session.getUri())) {
                    logger.debug("dispatch handler {}", handler.getClass().getName());
                    response = handler.onRequest(
                            this.webRoot == null ? AppProperty.getWebRoot() : this.webRoot, args, session);
                    break;
                }
            }

            if (response == null)
                // return no content ,if no handler.
                return newFixedLengthResponse(Status.NO_CONTENT, NanoHTTPD.MIME_PLAINTEXT, null);
            else
                return response;
        } catch (Exception e) {
            logger.error("Uncatch Exception:" + e.getMessage());
            return newFixedLengthResponse(Status.INTERNAL_ERROR, NanoHTTPD.MIME_PLAINTEXT, e.getMessage());
        }
    }
}
