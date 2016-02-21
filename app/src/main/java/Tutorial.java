import uk.co.caprica.vlcj.component.EmbeddedMediaListPlayerComponent;
import uk.co.caprica.vlcj.discovery.NativeDiscovery;

import javax.swing.*;
import java.io.IOException;

/**
 * Created by forDream on 2015-12-26.
 */
public class Tutorial {
    //private final JFrame frame;

    //private final EmbeddedMediaPlayerComponent mediaPlayerComponent;
    //private final EmbeddedMediaListPlayerComponent mediaListPlayerComponent;

    public static void main(final String[] args) throws IOException {
        new NativeDiscovery().discover();
        new EmbeddedMediaListPlayerComponent().getMediaList();
        /*
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Tutorial(args);
            }
        });
        */
        SwingUtilities.invokeLater(() -> new Tutorial(args));
//        DreamHttp http=new DreamHttp("C:\\Users\\forDream\\Documents\\GitHub\\Family-Vod-J\\web");
//        http.registerHandler(new StaticResourcesHandler());
//        http.start();
//        System.out.println(http.getListeningPort());
//        //new AnnotationConfigApplicationContext("vod.spring");
//        System.in.read();
        //System.out.println(HttpClient.head("http://218.205.82.147/videos/v0/20151228/ec/1d/3bb20865a5e1a28557f6f2bc29b8342e.f4v?key=019e6e75bc30830bb6fd86278da2a1373&src=iqiyi.com&tn=1451274766&qyid=4cd217df765c4908a37260ab8cd4b214&su=24d30f0784884238b2abce4f2dcd5d6e&bt=&client=&z=&ct=&uuid=700b4162-5680b212-20",new HashMap<>()));
    }

    public Tutorial(String[] args) {

    }
}
