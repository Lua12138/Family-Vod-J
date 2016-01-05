package vod.player;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.co.caprica.vlcj.binding.internal.libvlc_media_t;
import uk.co.caprica.vlcj.component.EmbeddedMediaListPlayerComponent;
import uk.co.caprica.vlcj.player.MediaMetaData;
import uk.co.caprica.vlcj.player.list.MediaListPlayer;
import vod.util.AppProperty;

/**
 * Created by forDream on 2015-12-31.
 */
public class DreamMediaListPlayerComponent extends EmbeddedMediaListPlayerComponent {
    private static final Logger logger = LoggerFactory.getLogger(DreamMediaListPlayerComponent.class);

    //protected final MediaListPlayer mediaListPlayer;

    //protected final MediaList mediaList;
/*
    public DreamMediaListPlayerComponent() {
        // Create the native resources
        DreamMediaPlayerFactory mediaPlayerFactory = null;
        MediaPlayerFactory factory = getMediaPlayerFactory();
        if (factory instanceof DreamMediaPlayerFactory)
            mediaPlayerFactory = (DreamMediaPlayerFactory) factory;
        mediaListPlayer = mediaPlayerFactory.newMediaListPlayer();
        mediaList = mediaPlayerFactory.newMediaList();

        mediaListPlayer.setMediaList(mediaList);
        mediaListPlayer.setMediaPlayer(getMediaPlayer());
        // Register listeners
        mediaListPlayer.addMediaListPlayerEventListener(this);
        // Sub-class initialisation
        onAfterConstruct();
    }
*/
/*
    @Override
    protected MediaPlayerFactory onGetMediaPlayerFactory() {
        try {
            Class clazz = Class.forName("uk.co.caprica.vlcj.component.Arguments");
            Method mergeArguments = clazz.getDeclaredMethod("mergeArguments", String[].class, String[].class);
            mergeArguments.setAccessible(true);
            String[] args = (String[]) mergeArguments.invoke(clazz, onGetMediaPlayerFactoryArgs(), onGetMediaPlayerFactoryExtraArgs());
            this.logger.debug("args={}", Arrays.toString(args));
            AnnotationConfigApplicationContext springContext = AppProperty.getSpringContext();
            MediaPlayerFactory factory = springContext.getBean(DreamMediaPlayerFactory.class, new Object[]{args});
            return factory;
            //return AppProperty.getSpringContext().getBean(DreamMediaPlayerFactory.class, new Object[]{args});
            //return new DreamMediaPlayerFactory(args);
        } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
            this.logger.error("Error:{}, Message:{}", e.getClass().getName(), e.getMessage());
            return null;
        }
    }
*/

    /**
     * Template method invoked before the media list player is released.
     */
    protected void onBeforeReleaseComponent() {
    }

    @Override
    public void played(MediaListPlayer mediaListPlayer) {
        System.out.println("played");
        System.out.println(mediaListPlayer.currentMrl());
        logger.debug("played({})", mediaListPlayer.currentMrl());
    }

    @Override
    public void nextItem(MediaListPlayer mediaListPlayer, libvlc_media_t item, String itemMrl) {
        logger.debug("nextItem({},libvlc_media_t,{})", mediaListPlayer.currentMrl(), itemMrl);
        // mark play status
        DreamPlayListHelper helper = AppProperty.getSpringContext().getBean(DreamPlayListHelper.class);
        if (mediaListPlayer.currentMrl() != null) {
            String mrl = mediaListPlayer.currentMrl();
            MediaMetaData data = helper.get(mrl);
            if (data == null) {
                logger.error("no matching item : {}", mrl);
                helper.refresh();
            } else
                data.setNowPlaying("played");
        }

        MediaMetaData data = helper.get(itemMrl);
        if (data != null) {
            data.setNowPlaying("playing");
        } else {
            logger.error("no matching item : {}", itemMrl);
        }
    }

    @Override
    public void stopped(MediaListPlayer mediaListPlayer) {

    }

    @Override
    public void mediaMetaChanged(MediaListPlayer mediaListPlayer, int metaType) {

    }

    @Override
    public void mediaSubItemAdded(MediaListPlayer mediaListPlayer, libvlc_media_t subItem) {

    }

    @Override
    public void mediaDurationChanged(MediaListPlayer mediaListPlayer, long newDuration) {

    }

    @Override
    public void mediaParsedChanged(MediaListPlayer mediaListPlayer, int newStatus) {

    }

    @Override
    public void mediaFreed(MediaListPlayer mediaListPlayer) {

    }

    @Override
    public void mediaStateChanged(MediaListPlayer mediaListPlayer, int newState) {

    }
}
