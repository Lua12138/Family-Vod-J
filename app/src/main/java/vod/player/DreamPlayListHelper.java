package vod.player;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.co.caprica.vlcj.component.EmbeddedMediaListPlayerComponent;
import uk.co.caprica.vlcj.medialist.MediaListItem;
import uk.co.caprica.vlcj.player.MediaMetaData;
import vod.util.AppProperty;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by forDream on 2016-01-04.
 */
public class DreamPlayListHelper {
    public enum VideoType {VOD, IPTV}

    protected final EmbeddedMediaListPlayerComponent player;
    protected List<DreamMediaItem> list;
    protected VideoType videoType;
    protected float vodPosition;

    private Logger logger = LoggerFactory.getLogger(DreamPlayListHelper.class);

    public DreamPlayListHelper(EmbeddedMediaListPlayerComponent player) {
        this.player = player;
        this.list = new LinkedList<>();
        this.videoType = VideoType.VOD;
    }

    public VideoType getVideoType() {
        return videoType;
    }

    /**
     * add new item to playlist
     *
     * @param mrl          the mrl of item
     * @param mediaOptions options
     * @return the MediaMetaData of added item.
     */
    public MediaMetaData add(String mrl, String... mediaOptions) {
        DreamMediaItem item = new DreamMediaItem(mrl, new MediaMetaData());
        int itemIndex = this.list.indexOf(item);
        if (itemIndex == -1) {
            item.getData().setUrl(mrl);
            item.getData().setNowPlaying("wait");
            this.list.add(item);
            player.getMediaList().addMedia(mrl, mediaOptions);
        } else {
            item = this.list.get(itemIndex);
        }
        return item.getData();
    }

    /**
     * add new item to specified position.
     *
     * @param pos          position
     * @param mrl          mrl
     * @param mediaOptions options
     * @return the MediaMetaData of added item.
     */
    public MediaMetaData add(int pos, String mrl, String... mediaOptions) {
        DreamMediaItem item = new DreamMediaItem(mrl, new MediaMetaData());
        int itemIndex = this.list.indexOf(item);
        if (itemIndex == -1) {
            item.getData().setUrl(mrl);
            this.list.add(pos, item);
            player.getMediaList().insertMedia(pos, mrl, mediaOptions);
        } else {
            item = this.list.get(itemIndex);
        }
        return item.getData();
    }

    /**
     * remove item from playlist
     *
     * @param index the index of item
     * @return removed MediaMetaData, else return null
     */
    public MediaMetaData remove(int index) {
        DreamMediaItem item = this.list.get(index);

        MediaListItem listItem = player.getMediaList().items().get(index);

        if (!listItem.mrl().equals(item.getMrl())) {
            logger.error("remove({}) playlist mismatching.");
            throw new Error("playlist error");
        }

        this.list.remove(index);
        player.getMediaList().removeMedia(index);

        return item.getData();
    }

    public MediaMetaData get(int index) {
        return this.list.get(index).getData();
    }

    public MediaMetaData get(String mrl) {
        DreamMediaItem item = new DreamMediaItem(mrl, new MediaMetaData());
        int index = this.list.indexOf(item);
        if (index != -1)
            return this.get(index);
        return null;
    }

    /**
     * @return play list count
     */
    public int size() {
        int selfSize = this.list.size();
        int vlcSize = player.getMediaList().size();

        if (selfSize != vlcSize) {
            logger.error("size() mismatching.");
            throw new Error("size error");
        }

        return vlcSize;
    }

    public void refresh() {
        logger.warn("start to refresh local play list.");
        logger.debug("local list count {} ,vlc list count {}", this.list.size(), this.player.getMediaList().size());
        List<DreamMediaItem> list = new LinkedList<>();
        List<MediaListItem> vlcMediaList = this.player.getMediaList().items();
        for (int i = 0; i < vlcMediaList.size(); i++) {
            MediaMetaData data = this.get(vlcMediaList.get(i).mrl());
            if (data == null) {
                logger.warn("sync vlc and local play list error, no matching item: {}", vlcMediaList.get(i).mrl());
                data = new MediaMetaData();
                data.setNowPlaying("unknown - refresh no matching");
            }
            DreamMediaItem item = new DreamMediaItem(vlcMediaList.get(i).mrl(), data);
        }

        this.list = list;
    }

    /**
     * play / resume to player
     */
    public void playOrResme() {
        player.getMediaPlayer().play();
    }

    /**
     * stop to play iptv
     *
     * @param mrl          iptv source
     * @param mediaOptions options
     */
    public void playIPTV(String mrl, String... mediaOptions) {
        this.vodPosition = player.getMediaPlayer().getPosition();
        this.videoType = VideoType.IPTV;
        player.getMediaListPlayer().stop();
        player.getMediaPlayer().stop();
        player.getMediaPlayer().playMedia(mrl, mediaOptions);
    }


    /**
     * stop to play vod playlist
     */
    public synchronized void resumeVod() {
        if (this.videoType == VideoType.IPTV) {
            // log vlc and local playlist
            if (AppProperty.isDebug()) {
                List<MediaListItem> list = player.getMediaListPlayer().getMediaList().items();
                int vlcTotal = list.size();
                int localTotal = this.list.size();
                int min = vlcTotal < localTotal ? vlcTotal : localTotal;
                logger.debug("vlc -> {} , local -> {}", vlcTotal, localTotal);
                for (int i = 0; i < min; i++) {
                    logger.debug("vlc list : {} / {} -> {}", i + 1, min, list.get(i).mrl());
                    logger.debug("local list : {} / {} -> {}", i + 1, min, this.list.get(i).data.getUrl());
                }

                if (vlcTotal > localTotal) {
                    for (int i = localTotal; i < vlcTotal; i++)
                        logger.debug("vlc addition {} / {} -> {}", i, vlcTotal, list.get(i).mrl());
                } else if (localTotal > vlcTotal) {
                    for (int i = vlcTotal; i < localTotal; i++)
                        logger.debug("local addition {} / {} -> {}", i, localTotal, this.list.get(i).data.getUrl());
                }
            }

            // resume vod
            player.getMediaPlayer().stop();
            player.getMediaListPlayer().stop();
            player.getMediaList().clear();
            for (int i = 0; i < this.list.size(); i++) {
                player.getMediaList().addMedia(this.get(i).getUrl());
                if ("playing".equals(this.get(i).getNowPlaying())) {
                    player.getMediaListPlayer().playItem(i);
                    player.getMediaPlayer().setPosition(this.vodPosition);
                    this.videoType = VideoType.VOD;
                    //player.getMediaListPlayer().getMediaList()
                    logger.debug("resume item {} -> {}", i, this.list.get(i).getMrl());
                }
            }
        }
    }

    public void playOrResumePlaylist() {
        player.getMediaListPlayer().play();
    }

    public boolean pause() {
        if (player.getMediaPlayer().canPause())
            player.getMediaPlayer().pause();
        else
            return false;
        return true;
    }

    public void pausePlaylist() {
        player.getMediaListPlayer().pause();
    }
}
