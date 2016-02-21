package vod.player;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.binding.internal.libvlc_instance_t;
import uk.co.caprica.vlcj.medialist.MediaList;
import uk.co.caprica.vlcj.player.list.DefaultMediaListPlayer;

/**
 * Created by forDream on 2016-01-02.
 */
public class DreamMediaListPlayer extends DefaultMediaListPlayer {
    private final Logger logger = LoggerFactory.getLogger(DreamMediaList.class);

    /**
     * Create a new media list player.
     *
     * @param libvlc   native library interface
     * @param instance libvlc instance
     */
    public DreamMediaListPlayer(LibVlc libvlc, libvlc_instance_t instance) {
        super(libvlc, instance);
    }

    @Override
    public void setMediaList(MediaList mediaList) {
        super.setMediaList(mediaList);
    }
}
