package by.liudchyk.audiotracks.dao;

/**
 * Created by Admin on 24.12.2016.
 */

import by.liudchyk.audiotracks.database.ProxyConnection;
import by.liudchyk.audiotracks.entity.Entity;
import by.liudchyk.audiotracks.entity.Track;
import by.liudchyk.audiotracks.exception.DAOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractDAO<T extends Entity> {
    private static final Logger LOG = LogManager.getLogger();
    protected ProxyConnection connection;

    public AbstractDAO(ProxyConnection connection) {
        this.connection = connection;
    }

    public void closeStatement(Statement statement) {
        try {
            if (statement != null) {
                statement.close();
            } else {
                LOG.warn("Statement was null");
            }
        } catch (SQLException e) {
            LOG.error("Mistake in statement closing", e);
        }
    }

    public void closeConnection(ProxyConnection connection){
        try {
            connection.close();
        } catch (SQLException e) {
            LOG.error("Connection can't be returned into pull", e);
        }
    }

    public List<Track> takeTracks(ResultSet set) throws SQLException {
        List<Track> tracks;
        tracks = new ArrayList<>();
        while (set.next()) {
            int id = set.getInt(1);
            String title = set.getString(2);
            String genre = set.getString(3);
            String artist = set.getString(4);
            double price = set.getDouble(5);
            int length = set.getInt(6);
            tracks.add(new Track(id, title, genre, price, length, artist));
        }
        return tracks;
    }
}
