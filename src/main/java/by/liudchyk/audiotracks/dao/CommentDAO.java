package by.liudchyk.audiotracks.dao;

import by.liudchyk.audiotracks.database.ProxyConnection;
import by.liudchyk.audiotracks.exception.DAOException;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by Admin on 03.01.2017.
 */
public class CommentDAO extends AbstractDAO {
    private final String DATE_PATTERN = "y-MM-DD HH:mm:ss";
    private final String SQL_ADD_COMMENT = "INSERT INTO comments(date,track_id,user_id,text) \n" +
            "VALUES (?,?,?,?)";

    public CommentDAO(ProxyConnection connection) {
        super(connection);
    }

    public boolean addComment(Date date, String text, int userId, int trackId) throws DAOException {
        SimpleDateFormat format = new SimpleDateFormat(DATE_PATTERN);
        String df = format.format(date);
        boolean isAdded;
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
            } else {
                isAdded = false;
            }
        } catch (SQLException e) {
            isAdded = false;
        } finally {
            closeStatement(statement);
        }
        return isAdded;
    }
}
