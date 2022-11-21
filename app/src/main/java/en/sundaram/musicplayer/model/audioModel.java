package en.sundaram.musicplayer.model;

import java.io.Serializable;

public class audioModel implements Serializable {
    String path;
    String title;
    String duration;

    public audioModel(String path, String title, String duration) { //constructor generated
        this.path = path;
        this.title = title;
        this.duration = duration;
    }

    public String getPath() {
        return path;
    } //getter and setter generated

    public void setPath(String path) {
        this.path = path;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}
