package en.sundaram.musicplayer.ui.theme;

public class themeModel {

    // string theme_name for storing course_name
    // and imgid for storing image id.
    private String theme_name;
    private int imgId;

    public themeModel(String theme_name, int imgid) {
        this.theme_name = theme_name;
        this.imgId = imgid;
    }

    public String getTheme_name() {
        return theme_name;
    }

    public void setTheme_name(String theme_name) {
        this.theme_name = theme_name;
    }

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }
}
