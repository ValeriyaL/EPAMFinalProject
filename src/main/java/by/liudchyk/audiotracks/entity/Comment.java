package by.liudchyk.audiotracks.entity;

/**
 * Created by Admin on 02.01.2017.
 */
public class Comment extends Entity {
    private String text;
    private String user;
    private String date;

    public Comment(String text, String user, String date) {
        this.text = text;
        this.user = user;
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public String getUser() {
        return user;
    }

    public String getDate() {
        return date;
    }
}
