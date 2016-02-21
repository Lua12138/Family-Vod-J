package vod;

import com.sun.jna.NativeLibrary;
import fi.iki.elonen.NanoHTTPD;
import fordream.http.DreamHttpd;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.component.EmbeddedMediaListPlayerComponent;
import uk.co.caprica.vlcj.discovery.NativeDiscovery;
import uk.co.caprica.vlcj.player.embedded.DefaultAdaptiveRuntimeFullScreenStrategy;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;
import vod.player.DreamPlayListHelper;
import vod.util.AppProperty;

import javax.swing.*;
import java.io.IOException;
import java.net.InetAddress;

/**
 * Created by forDream on 2015-12-27.
 */
public class Startup {
    private static AnnotationConfigApplicationContext springContext;

    private JFrame frame;
    private EmbeddedMediaListPlayerComponent mediaListPlayerComponent;
    private NanoHTTPD httpd;
    private String httpUrl;

    private static final Logger logger = LoggerFactory.getLogger(Startup.class);

    public static void main(String[] args) {
        // vlc
        String vlc = AppProperty.getVlcRoot();
        boolean b = new NativeDiscovery().discover();
        if (vlc != null && !b) {
            NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(), vlc);
            System.out.println(LibVlc.INSTANCE.libvlc_get_version());
            b = LibVlc.INSTANCE != null;
        }

        if (b) {
            springContext = new AnnotationConfigApplicationContext("vod.spring");
            AppProperty.setSpringContext(springContext);

//            System.out.println(System.getProperty("user.dir"));//user.dir指定了当前的路径
//
//            System.out.println(MimeHelper.getMime("aaa.html"));
//            System.out.println(MimeHelper.getMime("aa.b.a.css"));

//            if (b)
//                return;
            SwingUtilities.invokeLater(() -> new Startup());
        } else {
            logger.error("Cannot find vlc lib, exit application");
        }
    }

    public Startup() {
        // init
        frame = springContext.getBean(JFrame.class);
        mediaListPlayerComponent = springContext.getBean(EmbeddedMediaListPlayerComponent.class);
        DreamHttpd dreamHttp = springContext.getBean(DreamHttpd.class);

        //dreamHttp.setWebRoot(AppProperty.getWebRoot());

        httpd = dreamHttp;

        try {
            httpd.start();
            httpUrl = String.format("http://%s:%d/", InetAddress.getLocalHost().getHostAddress(), httpd.getListeningPort());
            AppProperty.set(AppProperty.P_KEY_WEBACCESS, httpUrl);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("err in start httpd: " + e.getMessage());
        }

        logger.info(httpUrl);

        frame.setBounds(0, 0, 500, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(mediaListPlayerComponent);
        frame.setVisible(true);
/*
        File f = QrCodeHelper.build(httpUrl);

        if (f.exists()) {
            //mediaListPlayerComponent.getMediaPlayer().playMedia(f.getAbsolutePath());
            mediaListPlayerComponent.getMediaListPlayer().getMediaList().addMedia(f.getAbsolutePath());
            mediaListPlayerComponent.getMediaListPlayer().play();
        }
*/
        // full screen
//        String osName = System.getProperty("os.name", "unknown").toLowerCase();
//        if (osName.startsWith("win"))
//            mediaListPlayerComponent.getMediaPlayer().setFullScreenStrategy(new Win32FullScreenStrategy(frame));
//        else if (osName.startsWith("linux"))
//            mediaListPlayerComponent.getMediaPlayer().setFullScreenStrategy(new XFullScreenStrategy(frame));
//        else
        mediaListPlayerComponent.getMediaPlayer().setFullScreenStrategy(new DefaultAdaptiveRuntimeFullScreenStrategy(frame));
/*
        List<MediaListItem> subItem = new ArrayList<>();
        subItem.add(new MediaListItem("大爆炸1", "D:\\test-ffmpeg\\00-10.mp4", new ArrayList<MediaListItem>()));
        subItem.add(new MediaListItem("大爆炸2", "D:\\test-ffmpeg\\10-20.mp4", new ArrayList<MediaListItem>()));
        subItem.add(new MediaListItem("大爆炸3", "D:\\test-ffmpeg\\20-30.mp4", new ArrayList<MediaListItem>()));

        MediaListItem mediaListItem = new MediaListItem("大爆炸合计", "file://D:\\test-ffmpeg\\20-30.mp4", subItem);
*/
        //mediaListPlayerComponent.getMediaListPlayer().setMode(MediaListPlayerMode.DEFAULT);
        //System.err.println(mediaListPlayerComponent.getMediaListPlayer().getMediaList().items().size());
        //mediaListPlayerComponent.getMediaListPlayer().getMediaList().items().add(mediaListItem);
        //System.err.println(mediaListPlayerComponent.getMediaListPlayer().getMediaList().items().size());
        //mediaListPlayerComponent.getMediaListPlayer().getMediaList().addMedia("D:\\test-ffmpeg\\00-10.mp4");
        //mediaListPlayerComponent.getMediaListPlayer().getMediaList().addMedia("D:\\test-ffmpeg\\10-20.mp4");
        //mediaListPlayerComponent.getMediaListPlayer().getMediaList().addMedia("D:\\test-ffmpeg\\20-30.mp4");
        //mediaListPlayerComponent.getMediaListPlayer().play();
        //mediaListPlayerComponent.getMediaListPlayer().enableEvents();
        DreamPlayListHelper helper = springContext.getBean(DreamPlayListHelper.class);
        helper.add("file:///D:/test-ffmpeg/00-10.mp4").setTitle("大爆炸1");
        helper.add("file:///D:/test-ffmpeg/10-20.mp4").setTitle("大噶子2");
        helper.add("file:///D:/test-ffmpeg/20-30.mp4").setTitle("大爆炸3");
        mediaListPlayerComponent.getMediaListPlayer().play();
        System.out.println("Size -> " + helper.size());
    }
}
