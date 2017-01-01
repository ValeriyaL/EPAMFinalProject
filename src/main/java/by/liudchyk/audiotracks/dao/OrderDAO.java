package by.liudchyk.audiotracks.dao;

import by.liudchyk.audiotracks.database.ProxyConnection;
import by.liudchyk.audiotracks.entity.Track;
import by.liudchyk.audiotracks.entity.User;
import by.liudchyk.audiotracks.exception.DAOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
    private static final String SQL_SELECT_LAST_ORDERS = "SELECT tracks.id, tracks.title, genres.name AS genre,  tracks.artists, tracks.price, tracks.length\n" +
            "FROM orders\n" +
            "JOIN tracks ON orders.track_id=tracks.id\n" +
            "LEFT JOIN genres ON tracks.genre_id=genres.id\n" +
            "WHERE tracks.visible = 1\n" +
            "GROUP BY tracks.title ORDER BY orders.date DESC LIMIT 5 ";

    public OrderDAO(ProxyConnection connection) {
        super(connection);
    }

    @Override
    public List findAll() throws DAOException {
        return null;
    }

    public List<Track> findLastOrders() throws DAOException {
        List<Track> tracks = new ArrayList<>();
        Statement statement = null;
        try {
            statement = connection.createStatement();
            ResultSet set = statement.executeQuery(SQL_SELECT_LAST_ORDERS);
            tracks = takeTracks(set);
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            closeStatement(statement);
        }
        return tracks;
    }
}
