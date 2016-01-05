package vod.spring;

import fi.iki.elonen.NanoHTTPD;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import vod.http.DreamHttp;
import vod.http.RequestHandler;
import vod.http.handler.*;
import vod.util.AppProperty;

import java.util.Random;

/**
 * Created by forDream on 2016-01-04.
 */
@Configuration
public class HttpBeanConfig {
    @Bean
    public NanoHTTPD httpd() {
        DreamHttp http;
        if (AppProperty.isDebug())
            http = new DreamHttp(80);
        else
            http = new DreamHttp(new Random().nextInt(55535) + 10000);
        http.registerHandler(videoTransferHandler());
        http.registerHandler(imageTransferHandler());
        http.registerHandler(playStatusHandler());
        http.registerHandler(playAndResumeHandler());
        http.registerHandler(playListHandler());
        http.registerHandler(iptvHandler());
        http.registerHandler(staticResHandler());
        return http;
    }

    @Bean
    public RequestHandler staticResHandler() {
        return new StaticResourcesHandler();
    }

    @Bean
    public RequestHandler videoTransferHandler() {
        return new VideoTranferHandler();
    }

    @Bean
    public RequestHandler playAndResumeHandler() {
        return new PlayAndResumeHandler();
    }

    @Bean
    public RequestHandler playStatusHandler() {
        return new PlayStatusHandler();
    }

    @Bean
    public RequestHandler imageTransferHandler() {
        return new ImageTransferHandler();
    }

    @Bean
    public RequestHandler iptvHandler() {
        return new IptvHandler();
    }

    @Bean
    RequestHandler playListHandler() {
        return new PlayListHandler();
    }
}
