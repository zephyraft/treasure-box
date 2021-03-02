package zephyr.poi.model;

import java.util.Set;


public class Postcard {

    Integer id;
    String title;
    String coverUrl;
    String albumId;
    Set<String> copywriteSet;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public String getAlbumId() {
        return albumId;
    }

    public void setAlbumId(String albumId) {
        this.albumId = albumId;
    }

    public Set<String> getCopywriteSet() {
        return copywriteSet;
    }

    public void setCopywriteSet(Set<String> copywriteSet) {
        this.copywriteSet = copywriteSet;
    }

    @Override
    public String toString() {
        return "Postcard{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", coverUrl='" + coverUrl + '\'' +
                ", albumId='" + albumId + '\'' +
                ", copywriteSet=" + copywriteSet +
                '}';
    }
}
