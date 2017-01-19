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
 * Created by Admin on 28.12.2016.
 */
public class OrderDAO extends AbstractDAO {
    private static final String SQL_SELECT_MOST_ORDERED = "SELECT tracks.id, tracks.title, genres.name AS genre,  tracks.artists, tracks.price, tracks.length, count(*) AS count \n" +
            "FROM orders\n" +
            "LEFT JOIN tracks ON tracks.id = track_id\n" +
            "LEFT JOIN genres ON tracks.genre_id=genres.id\n" +
            "GROUP BY tracks.id ORDER BY count DESC LIMIT 5;";
    private static final String SQL_SELECT_ORDERS_BY_USER = "SELECT tracks.id, tracks.title, genres.name AS genre,  tracks.artists, tracks.price, tracks.length\n" +
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

    public List<Track> findOrdersByUser(User user) throws DAOException {
        List<Track> tracks = new ArrayList<>();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_SELECT_ORDERS_BY_USER);
            statement.setString(1, Integer.toString(user.getId()));
            ResultSet set = statement.executeQuery();
            tracks = takeTracks(set);
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            closeStatement(statement);
        }
        return tracks;
    }

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
}
