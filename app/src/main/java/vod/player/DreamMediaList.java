package vod.player;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.binding.internal.libvlc_instance_t;
import uk.co.caprica.vlcj.binding.internal.libvlc_media_t;
import uk.co.caprica.vlcj.medialist.MediaList;
import vod.reflect.ReflectHelper;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by forDream on 2016-01-02.
 */
public class DreamMediaList extends MediaList {
    private Logger logger = LoggerFactory.getLogger(DreamMediaList.class);

    protected LibVlc libvlc;
    protected List<MediaItem> list;

    public DreamMediaList(LibVlc libvlc, libvlc_instance_t instance) {
        super(libvlc, instance);
        this.libvlc = libvlc;
        this.list = new LinkedList<>();
    }

    protected void unlock() {
        ReflectHelper.invokeMethod(this, "unlock", null, null);
    }

    protected void lock() {
        ReflectHelper.invokeMethod(this, "lock", null, null);
    }

    public MediaItem newMedia(String mrl) {
        logger.debug("newMedia({})", mrl);
        Object object = ReflectHelper.invokeMethod(this, "newMediaDescriptor", new Class[]{String.class, String[].class}, new String[]{mrl, null});
        if (object != null) {
            // create success
            return new MediaItem(this.libvlc, (libvlc_media_t) object);
        } else {
            logger.error("Create New libvlc_media_t fail.");
            return null;
        }
    }

    /**
     * add new media item to playlist.
     *
     * @param item
     * @return
     */
    public boolean add(MediaItem item) {
        lock();
        int result = this.libvlc.libvlc_media_list_add_media(mediaListInstance(), item.libvlc_media_t);
        libvlc.libvlc_media_release(item.libvlc_media_t);
        if (result == 0)
            this.list.add(item);
        unlock();
        return result == 0;
    }

    public MediaItem remove(MediaItem item) {
        lock();
        int index = this.libvlc.libvlc_media_list_index_of_item(mediaListInstance(), item.libvlc_media_t);
        if (index != -1) {
            this.libvlc.libvlc_media_list_remove_index(mediaListInstance(), index);
            this.list.remove(item);
            return item;
        }
        unlock();
        return null;
    }

    public MediaItem remove(int index) {
        lock();
        this.libvlc.libvlc_media_list_remove_index(mediaListInstance(), index);
        unlock();
        return this.list.remove(index);
    }

    public int count() {
        int vlcCount = this.libvlc.libvlc_media_list_count(mediaListInstance());
        if (vlcCount != this.list.size()) {
            logger.error("Inner playlist error with vlc playlist");
        }
        return vlcCount;
    }

    public void insert(MediaItem item, int pos) {
        lock();

        unlock();
    }
}
