package by.liudchyk.audiotracks.dao;

import by.liudchyk.audiotracks.database.ProxyConnection;
import by.liudchyk.audiotracks.entity.Track;
import by.liudchyk.audiotracks.entity.User;
import by.liudchyk.audiotracks.exception.DAOException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Class {@code OrdersDAO} is used to connect with data base.
 * Does all actions connected with orders.
 *
 * @author Liudchyk Valeriya
 * @see AbstractDAO
 */
@SuppressWarnings("Duplicates")
public class OrderDAO extends AbstractDAO {
    private static final String SQL_SELECT_MOST_ORDERED = "SELECT tracks.id, tracks.title, genres.name AS genre,  tracks.artists, tracks.price, tracks.length, count(*) AS count \n" +
            "FROM orders\n" +
            "LEFT JOIN tracks ON tracks.id = track_id\n" +
            "LEFT JOIN genres ON tracks.genre_id=genres.id\n" +
            "GROUP BY tracks.id ORDER BY count DESC LIMIT 5;";
    private static final String SQL_SELECT_ORDERS_BY_USER = "SELECT tracks.id, tracks.title, genres.name AS genre,  tracks.artists, tracks.price, tracks.length, tracks.visible\n" +
            "FROM orders\n" +
            "JOIN tracks ON tracks.id=orders.track_id\n" +
            "LEFT JOIN genres ON tracks.genre_id=genres.id\n" +
            "WHERE user_id=?\n" +
            "ORDER BY tracks.title";
    private static final String SQL_COUNT_ORDERS = "SELECT COUNT(*) \n" +
            "FROM orders \n" +
            "WHERE user_id=? AND track_id=?";
    private static final String SQL_ADD_ORDER = "INSERT INTO orders(track_id, price, user_id, date) VALUES(?,?,?,?);";

    public OrderDAO(ProxyConnection connection) {
        super(connection);
    }

    /**
     * Find 5 most ordered tracks
     *
     * @return List of 5 most ordered tracks
     * @throws DAOException if thrown SQLException
     */
    public List<Track> findMostOrdered() throws DAOException {
        List<Track> tracks = new ArrayList<>();
        Statement statement = null;
        try {
            statement = connection.createStatement();
            ResultSet set = statement.executeQuery(SQL_SELECT_MOST_ORDERED);
            tracks = takeTracks(set);
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            closeStatement(statement);
        }
        return tracks;
    }

    /**
     * Find all user's ordered tracks
     *
     * @param user is User object
     * @return Last of ordered tracks
     * @throws DAOException if thrown SQLException
     */
    public List<Track> findOrdersByUser(User user) throws DAOException {
        List<Track> tracks;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_SELECT_ORDERS_BY_USER);
            statement.setString(1, Integer.toString(user.getId()));
            ResultSet set = statement.executeQuery();
            tracks = takeTracksWithVisible(set);
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            closeStatement(statement);
        }
        return tracks;
    }

    /**
     * Checks is order exist
     *
     * @param userId  is user's id
     * @param trackId is track's id
     * @return true if order exists, false otherwise
     * @throws DAOException if was thrown SQLException
     */
    public boolean isOrderExist(int userId, int trackId) throws DAOException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_COUNT_ORDERS);
            statement.setInt(1, userId);
            statement.setInt(2, trackId);
            ResultSet set = statement.executeQuery();
            set.next();
            return set.getInt(1) == 1;
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            closeStatement(statement);
        }
    }

    /**
     * Adds order to database
     *
     * @param trackId    is track's id
     * @param price      is order price
     * @param userId     is user's id
     * @param formatDate is date of order in string format
     * @throws DAOException if was thrown SQLException
     */
    public void addOrder(int trackId, double price, int userId, String formatDate) throws DAOException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_ADD_ORDER);
            statement.setInt(1, trackId);
            statement.setDouble(2, price);
            statement.setInt(3, userId);
            statement.setString(4, formatDate);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            closeStatement(statement);
        }
    }

    /**
     * Takes all visible tracks from ResultSet
     *
     * @param set is ResultSet object
     * @return list with all tracks
     * @throws DAOException if SQLException was thrown
     */
    private List<Track> takeTracksWithVisible(ResultSet set) throws DAOException {
        List<Track> tracks;
        tracks = new ArrayList<>();
        try {
            while (set.next()) {
                int id = set.getInt(1);
                String title = set.getString(2);
                String genre = set.getString(3);
                String artist = set.getString(4);
                double price = set.getDouble(5);
                int length = set.getInt(6);
                int visible = set.getInt(7);
                tracks.add(new Track(id, title, genre, price, length, artist, visible));
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return tracks;
    }
}
