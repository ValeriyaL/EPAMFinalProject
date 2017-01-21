package by.liudchyk.audiotracks.entity;

/**
 * Class {@code Comment} is used to store Comment entity.
 *
 * @author Liudchyk Valeriya
 * @version 1.0
 * @see Entity
 */
public class Comment extends Entity {
    /**
     * The comment's text.
     */
    private String text;
    /**
     * The user's nickname of author of the comment.
     */
    private String user;
    /**
     * The comment's date in string form.
     */
    private String date;

    /**
     * Creates new object of Comment entity
     *
     * @param text is comment's text message
     * @param user is user's nickname of author of comment
     * @param date is comment's date
     */
    public Comment(String text, String user, String date) {
        this.text = text;
        this.user = user;
        this.date = date;
    }

    /**
     * Gets text of the comment
     *
     * @return comment's text
     */
    public String getText() {
        return text;
    }

    /**
     * Gets nickname of comment's author
     *
     * @return user nickname
     */
    public String getUser() {
        return user;
    }

    /**
     * Gets comment's date
     *
     * @return comment's date
     */
    public String getDate() {
        return date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Comment comment = (Comment) o;

        if (text != null ? !text.equals(comment.text) : comment.text != null) return false;
        if (user != null ? !user.equals(comment.user) : comment.user != null) return false;
        return date != null ? date.equals(comment.date) : comment.date == null;

    }

    @Override
    public int hashCode() {
        int result = text != null ? text.hashCode() : 0;
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        return result;
    }
}
