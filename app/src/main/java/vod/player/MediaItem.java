package vod.player;

import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.binding.internal.libvlc_media_t;
import uk.co.caprica.vlcj.binding.internal.libvlc_meta_t;
import uk.co.caprica.vlcj.player.MediaMeta;
import uk.co.caprica.vlcj.player.MediaMetaData;

import java.awt.image.BufferedImage;

/**
 * Created by forDream on 2016-01-03.
 */
public class MediaItem implements MediaMeta {
    protected final LibVlc libvlc;
    protected final libvlc_media_t libvlc_media_t;

    public MediaItem(LibVlc libvlc, libvlc_media_t libvlc_media_t) {
        this.libvlc = libvlc;
        this.libvlc_media_t = libvlc_media_t;
    }

    protected String getValue(libvlc_meta_t type) {
        return this.libvlc.libvlc_media_get_meta(this.libvlc_media_t, type.intValue()).getString(0);
    }

    protected void setValue(libvlc_meta_t type, String value) {
        this.libvlc.libvlc_media_set_meta(this.libvlc_media_t, type.intValue(), value);
    }

    @Override
    public void parse() {

    }

    @Override
    public String getTitle() {
        return this.getValue(libvlc_meta_t.libvlc_meta_Title);
    }

    @Override
    public void setTitle(String title) {
        this.setValue(libvlc_meta_t.libvlc_meta_Title, title);
    }

    @Override
    public String getArtist() {
        return this.getValue(libvlc_meta_t.libvlc_meta_Artist);
    }

    @Override
    public void setArtist(String artist) {
        this.setValue(libvlc_meta_t.libvlc_meta_Artist, artist);
    }

    @Override
    public String getGenre() {
        return this.getValue(libvlc_meta_t.libvlc_meta_Genre);
    }

    @Override
    public void setGenre(String genre) {
        this.setValue(libvlc_meta_t.libvlc_meta_Genre, genre);
    }

    @Override
    public String getCopyright() {
        return this.getValue(libvlc_meta_t.libvlc_meta_Copyright);
    }

    @Override
    public void setCopyright(String copyright) {
        this.setValue(libvlc_meta_t.libvlc_meta_Copyright, copyright);
    }

    @Override
    public String getAlbum() {
        return this.getValue(libvlc_meta_t.libvlc_meta_Album);
    }

    @Override
    public void setAlbum(String album) {
        this.setValue(libvlc_meta_t.libvlc_meta_Album, album);
    }

    @Override
    public String getTrackNumber() {
        return this.getValue(libvlc_meta_t.libvlc_meta_TrackNumber);
    }

    @Override
    public void setTrackNumber(String trackNumber) {
        this.setValue(libvlc_meta_t.libvlc_meta_TrackNumber, trackNumber);
    }

    @Override
    public String getDescription() {
        return this.getValue(libvlc_meta_t.libvlc_meta_Description);
    }

    @Override
    public void setDescription(String description) {
        this.setValue(libvlc_meta_t.libvlc_meta_Description, description);
    }

    @Override
    public String getRating() {
        return this.getValue(libvlc_meta_t.libvlc_meta_Rating);
    }

    @Override
    public void setRating(String rating) {
        this.setValue(libvlc_meta_t.libvlc_meta_Rating, rating);
    }

    @Override
    public String getDate() {
        return this.getValue(libvlc_meta_t.libvlc_meta_Date);
    }

    @Override
    public void setDate(String date) {
        this.setValue(libvlc_meta_t.libvlc_meta_Date, date);
    }

    @Override
    public String getSetting() {
        return this.getValue(libvlc_meta_t.libvlc_meta_Setting);
    }

    @Override
    public void setSetting(String setting) {
        this.setValue(libvlc_meta_t.libvlc_meta_Setting, setting);
    }

    @Override
    public String getUrl() {
        return this.getValue(libvlc_meta_t.libvlc_meta_URL);
    }

    @Override
    public void setUrl(String url) {
        this.setValue(libvlc_meta_t.libvlc_meta_URL, url);
    }

    @Override
    public String getLanguage() {
        return this.getValue(libvlc_meta_t.libvlc_meta_Language);
    }

    @Override
    public void setLanguage(String language) {
        this.setValue(libvlc_meta_t.libvlc_meta_Language, language);
    }

    @Override
    public String getNowPlaying() {
        return this.getValue(libvlc_meta_t.libvlc_meta_NowPlaying);
    }

    @Override
    public void setNowPlaying(String nowPlaying) {
        this.setValue(libvlc_meta_t.libvlc_meta_NowPlaying, nowPlaying);
    }

    @Override
    public String getPublisher() {
        return this.getValue(libvlc_meta_t.libvlc_meta_Publisher);
    }

    @Override
    public void setPublisher(String publisher) {
        this.setValue(libvlc_meta_t.libvlc_meta_Publisher, publisher);
    }

    @Override
    public String getEncodedBy() {
        return this.getValue(libvlc_meta_t.libvlc_meta_EncodedBy);
    }

    @Override
    public void setEncodedBy(String encodedBy) {
        this.setValue(libvlc_meta_t.libvlc_meta_EncodedBy, encodedBy);
    }

    @Override
    public String getArtworkUrl() {
        return this.getValue(libvlc_meta_t.libvlc_meta_ArtworkURL);
    }

    @Override
    public void setArtworkUrl(String artworkUrl) {
        this.setValue(libvlc_meta_t.libvlc_meta_ArtworkURL, artworkUrl);
    }

    @Override
    public String getTrackId() {
        return this.getValue(libvlc_meta_t.libvlc_meta_TrackID);
    }

    @Override
    public void setTrackId(String trackId) {
        this.setValue(libvlc_meta_t.libvlc_meta_TrackID, trackId);
    }

    @Override
    public String getTrackTotal() {
        return this.getValue(libvlc_meta_t.libvlc_meta_TrackTotal);
    }

    @Override
    public void setTrackTotal(String trackTotal) {
        this.setValue(libvlc_meta_t.libvlc_meta_TrackTotal, trackTotal);
    }

    @Override
    public String getDirector() {
        return this.getValue(libvlc_meta_t.libvlc_meta_Director);
    }

    @Override
    public void setDirector(String director) {
        this.setValue(libvlc_meta_t.libvlc_meta_Director, director);
    }

    @Override
    public String getSeason() {
        return this.getValue(libvlc_meta_t.libvlc_meta_Season);
    }

    @Override
    public void setSeason(String season) {
        this.setValue(libvlc_meta_t.libvlc_meta_Season, season);
    }

    @Override
    public String getEpisode() {
        return this.getValue(libvlc_meta_t.libvlc_meta_Episode);
    }

    @Override
    public void setEpisode(String episode) {
        this.setValue(libvlc_meta_t.libvlc_meta_Episode, episode);
    }

    @Override
    public String getShowName() {
        return this.getValue(libvlc_meta_t.libvlc_meta_ShowName);
    }

    @Override
    public void setShowName(String showName) {
        this.setValue(libvlc_meta_t.libvlc_meta_ShowName, showName);
    }

    @Override
    public String getActors() {
        return this.getValue(libvlc_meta_t.libvlc_meta_Actors);
    }

    @Override
    public void setActors(String actors) {
        this.setValue(libvlc_meta_t.libvlc_meta_Actors, actors);
    }

    @Override
    public String getAlbumArtist() {
        return this.getValue(libvlc_meta_t.libvlc_meta_AlbumArtist);
    }

    @Override
    public void setAlbumArtist(String albumArtist) {
        this.setValue(libvlc_meta_t.libvlc_meta_AlbumArtist, albumArtist);
    }

    @Override
    public String getDiscNumber() {
        return this.getValue(libvlc_meta_t.libvlc_meta_DiscNumber);
    }

    @Override
    public void setDiscNumber(String discNumber) {
        this.setValue(libvlc_meta_t.libvlc_meta_DiscNumber, discNumber);
    }

    @Override
    @Deprecated
    public BufferedImage getArtwork() {
        return null;
    }

    @Override
    @Deprecated
    public long getLength() {
        return 0;
    }

    @Override
    public void save() {
        int result = this.libvlc.libvlc_media_save_meta(this.libvlc_media_t);
    }

    @Override
    public void release() {
        this.libvlc.libvlc_media_release(this.libvlc_media_t);
    }

    @Override
    public MediaMetaData asMediaMetaData() {
        MediaMetaData data = new MediaMetaData();
        data.setActors(this.getActors());
        data.setAlbum(this.getAlbum());
        data.setAlbumArtist(this.getAlbumArtist());
        data.setArtist(this.getArtist());
        data.setArtworkUrl(this.getArtworkUrl());

        data.setCopyright(this.getCopyright());

        data.setDate(this.getDate());
        data.setDescription(this.getDescription());
        data.setDirector(this.getDirector());
        data.setDiscNumber(this.getDiscNumber());

        data.setEncodedBy(this.getEncodedBy());
        data.setEpisode(this.getEpisode());

        data.setGenre(this.getGenre());

        data.setLanguage(this.getLanguage());

        data.setNowPlaying(this.getNowPlaying());

        data.setPublisher(this.getPublisher());

        data.setRating(this.getRating());

        data.setSeason(this.getSeason());
        data.setSetting(this.getSetting());
        data.setShowName(this.getShowName());

        data.setTitle(this.getTitle());
        data.setTrackId(this.getTrackId());
        data.setTrackNumber(this.getTrackNumber());
        data.setTrackTotal(this.getTrackTotal());

        data.setUrl(this.getUrl());

        return data;
    }
}
