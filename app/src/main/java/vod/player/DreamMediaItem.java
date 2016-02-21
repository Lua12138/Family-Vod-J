package vod.player;

import uk.co.caprica.vlcj.player.MediaMetaData;

/**
 * Created by forDream on 2016-01-04.
 */
public class DreamMediaItem {
    protected final String mrl;
    protected final MediaMetaData data;

    public DreamMediaItem(String mrl, MediaMetaData data) {
        this.mrl = mrl;
        this.data = data;
    }

    public String getMrl() {
        return mrl;
    }

    public MediaMetaData getData() {
        return data;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;

        if (obj instanceof DreamMediaItem) {
            return ((DreamMediaItem) obj).getMrl().equals(this.mrl);
        }
        return false;
    }
}
