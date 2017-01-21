package by.liudchyk.audiotracks.dao;

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

/**
 * Class {@code AbstractDAO} is used to connect with data base.
 * Class is a superclass for all DAO classes.
 *
 * @author Liudchyk Valeriya
 * @see by.liudchyk.audiotracks.entity.Entity
 */

public abstract class AbstractDAO<T extends Entity> {
    static final Logger LOG = LogManager.getLogger();
    ProxyConnection connection;

    /**
     * Fill connection that will be used in DAO method.
     *
     * @param connection is ProxyConnection object
     */
    public AbstractDAO(ProxyConnection connection) {
        this.connection = connection;
    }

    /**
     * Closes statement
     *
     * @param statement is Statement object
     */
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

    /**
     * Closes connection
     *
     * @param connection is ProxeConnection object
     */
    public void closeConnection(ProxyConnection connection) {
        try {
            connection.close();
        } catch (SQLException e) {
            LOG.error("Connection can't be returned into pull", e);
        }
    }

    /**
     * Takes all Tracks from ResultSet and puts it into List
     *
     * @param set is ResultSet of Tracks
     * @return List of tracks
     * @throws DAOException if SQLException was catched
     */
    public List<Track> takeTracks(ResultSet set) throws DAOException {
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
                tracks.add(new Track(id, title, genre, price, length, artist));
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return tracks;
    }
}
