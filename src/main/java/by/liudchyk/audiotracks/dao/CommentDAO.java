package by.liudchyk.audiotracks.dao;

import by.liudchyk.audiotracks.database.ProxyConnection;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Class {@code CommentDAO} is used to connect with data base.
 * Does all actions connected with comments.
 *
 * @author Liudchyk Valeriya
 * @see AbstractDAO
 */
public class CommentDAO extends AbstractDAO {
    private final String DATE_PATTERN = "y-MM-DD HH:mm:ss";
    private final String SQL_ADD_COMMENT = "INSERT INTO comments(date,track_id,user_id,text) \n" +
            "VALUES (?,?,?,?)";
    private final String SQL_DELETE_COMMENT = "DELETE FROM comments \n" +
            "WHERE comments.date=? \n" +
            "AND track_id=? \n" +
            "AND user_id=?";

    public CommentDAO(ProxyConnection connection) {
        super(connection);
    }

    public boolean addComment(Date date, String text, int userId, int trackId) {
        SimpleDateFormat format = new SimpleDateFormat(DATE_PATTERN);
        String df = format.format(date);
        boolean isAdded = false;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_ADD_COMMENT);
            statement.setString(1, df);
            statement.setString(4, text);
            statement.setString(3, Integer.toString(userId));
            statement.setString(2, Integer.toString(trackId));
            int i = statement.executeUpdate();
            if (i != 0) {
                isAdded = true;
            }
        } catch (SQLException e) {
            LOG.warn("SQLException in addComment", e);
        } finally {
            closeStatement(statement);
        }
        return isAdded;
    }

    public boolean deleteComment(int userId, int trackId, String date) {
        boolean isDeleted = false;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_DELETE_COMMENT);
            statement.setString(1, date);
            statement.setString(2, Integer.toString(trackId));
            statement.setString(3, Integer.toString(userId));
            int i = statement.executeUpdate();
            if (i != 0) {
                isDeleted = true;
            }
        } catch (SQLException e) {
            LOG.warn("SQLException in deleteComment", e);
        } finally {
            closeStatement(statement);
        }
        return isDeleted;
    }
}
