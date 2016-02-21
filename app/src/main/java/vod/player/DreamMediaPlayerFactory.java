package vod.player;

import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.medialist.MediaList;
import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.list.MediaListPlayer;

import java.util.List;

/**
 * Created by forDream on 2016-01-02.
 */
public class DreamMediaPlayerFactory extends MediaPlayerFactory {
    public DreamMediaPlayerFactory() {
    }

    public DreamMediaPlayerFactory(String... libvlcArgs) {
        super(libvlcArgs);
    }

    public DreamMediaPlayerFactory(LibVlc libvlc) {
        super(libvlc);
    }

    public DreamMediaPlayerFactory(LibVlc libvlc, String... libvlcArgs) {
        super(libvlc, libvlcArgs);
    }

    public DreamMediaPlayerFactory(List<String> libvlcArgs) {
        super(libvlcArgs);
    }

    public DreamMediaPlayerFactory(LibVlc libvlc, List<String> libvlcArgs) {
        super(libvlc, libvlcArgs);
    }

    @Override
    public MediaList newMediaList() {
        //return AppProperty.getSpringContext().getBean(DreamMediaList.class, this.libvlc, this.instance);
        return new DreamMediaList(libvlc, instance);
    }

    @Override
    public MediaListPlayer newMediaListPlayer() {
        //return AppProperty.getSpringContext().getBean(DreamMediaListPlayer.class, libvlc, instance);
        return new DreamMediaListPlayer(libvlc, instance);
    }

//    public MediaItem newMediaItem() {
//        return new MediaItem(libvlc, instance);
//    }
}
