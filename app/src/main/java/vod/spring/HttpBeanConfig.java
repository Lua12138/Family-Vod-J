package vod.spring;

import fi.iki.elonen.NanoHTTPD;
import fordream.http.DreamHttpd;
import fordream.http.RequestHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import vod.http.handler.*;
import vod.util.AppProperty;

import java.util.Random;

/**
 * The Spring Beans of httpd
 */
@Configuration
public class HttpBeanConfig {
    /**
     *
     * @return random port httpd
     */
    @Bean
    public NanoHTTPD httpd() {
        DreamHttpd http;
        if (AppProperty.isDebug())
            http = new DreamHttpd(80, System.getProperty("user.dir"));
        else
            http = new DreamHttpd(new Random().nextInt(55535) + 10000, System.getProperty("user.dir"));
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
