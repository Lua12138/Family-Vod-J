package vod.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import uk.co.caprica.vlcj.component.EmbeddedMediaListPlayerComponent;
import vod.player.DreamMediaListPlayerComponent;
import vod.player.DreamPlayListHelper;

/**
 * Created by forDream on 2016-01-02.
 */
@Configuration
public class PlayerBeanConfig {
    /*
        @Bean
        @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
        @Resource
        public DreamMediaPlayerFactory dreamMediaPlayerFactory(String[] args) {
            return new DreamMediaPlayerFactory(args);
        }

        @Bean
        @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
        @Resource
        public DreamMediaList dreamMediaList(LibVlc libvlc, libvlc_instance_t instance) {
            return new DreamMediaList(libvlc, instance);
        }

        @Bean
        @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
        @Resource
        public DreamMediaListPlayer dreamMediaListPlayer(LibVlc libvlc, libvlc_instance_t instance) {
            DreamMediaListPlayer player=new DreamMediaListPlayer(libvlc,instance);
            return player;
        }

        @Bean
        @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
        public DreamMediaListPlayerComponent dreamMediaListPlayerComponent() {
            return new DreamMediaListPlayerComponent();
        }
    */
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
