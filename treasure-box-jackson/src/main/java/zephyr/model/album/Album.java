package zephyr.model.album;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zephyr on 2019-06-27.
 */
public class Album {

    private String title;
    private String[] links;
    private List<String> songs;
    private Artist artist;
    private Map<String, String> musicians = new HashMap<>();

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String[] getLinks() {
        return links;
    }

    public void setLinks(String[] links) {
        this.links = links;
    }

    public List<String> getSongs() {
        return songs;
    }

    public void setSongs(List<String> songs) {
        this.songs = songs;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    public Map<String, String> getMusicians() {
        return musicians;
    }

    public void addMusician(String key, String value) {
        musicians.put(key, value);
    }
}
