import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import uk.co.caprica.vlcj.component.EmbeddedMediaListPlayerComponent;
import uk.co.caprica.vlcj.discovery.NativeDiscovery;
import uk.co.caprica.vlcj.player.embedded.DefaultAdaptiveRuntimeFullScreenStrategy;

import javax.swing.*;

/**
 * Created by forDream on 2015-12-27.
 */
public class PlayerTest {
    private JFrame frame;
    private EmbeddedMediaListPlayerComponent mediaListPlayerComponent;
    private String playUrl = "D:\\The Big Bang Theory S08\\test.mp4";

    @Before
    public void init() {
        new NativeDiscovery().discover();
        this.frame = new JFrame("My First Media Player");
        this.mediaListPlayerComponent = new EmbeddedMediaListPlayerComponent();
        frame.setBounds(100, 100, 600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mediaListPlayerComponent = new EmbeddedMediaListPlayerComponent();
        frame.setContentPane(mediaListPlayerComponent);
        frame.setVisible(true);
    }

    @After
    public void destroy() {
    }

    @Test
    public void singlePlay() {
        this.mediaListPlayerComponent.getMediaPlayer().playMedia(this.playUrl);
    }

    @Test
    public void fullscreen() {
        this.mediaListPlayerComponent.getMediaPlayer().
                setFullScreenStrategy(new DefaultAdaptiveRuntimeFullScreenStrategy(this.frame));
        this.mediaListPlayerComponent.getMediaPlayer().toggleFullScreen();
    }
}
