package vod.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import uk.co.caprica.vlcj.component.EmbeddedMediaListPlayerComponent;
import vod.player.DreamMediaListPlayerComponent;
import vod.player.DreamPlayListHelper;

/**
 * The Spring Beans of Player
 */
@Configuration
public class PlayerBeanConfig {
    @Bean
    public EmbeddedMediaListPlayerComponent mediaListPlayerComponent() {
        EmbeddedMediaListPlayerComponent player = new DreamMediaListPlayerComponent();
        return player;
    }

    @Bean
    public DreamPlayListHelper playListHelper() {
        return new DreamPlayListHelper(mediaListPlayerComponent());
    }
}
