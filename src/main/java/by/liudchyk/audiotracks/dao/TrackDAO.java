package by.liudchyk.audiotracks.dao;

import by.liudchyk.audiotracks.database.ProxyConnection;
import by.liudchyk.audiotracks.entity.Track;
import by.liudchyk.audiotracks.exception.DAOException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 01.01.2017.
 */
public class TrackDAO extends AbstractDAO {
    private static final String SQL_SELECT_ALL_IN_ORDER = "SELECT tracks.id, tracks.title, genres.name AS genre, tracks.artists, tracks.price, tracks.length\n" +
            "FROM tracks\n" +
            "LEFT JOIN genres ON tracks.genre_id=genres.id\n" +
            "WHERE visible=1\n" +
            "ORDER BY ";
    private static final String SQL_SELECT_ALL_IN_GENRE = "SELECT tracks.id, tracks.title, genres.name AS genre, tracks.artists, tracks.price, tracks.length\n" +
            "FROM tracks\n" +
            "LEFT JOIN genres ON tracks.genre_id=genres.id\n" +
            "WHERE visible=1\n" +
            "HAVING genre = ?";

    public TrackDAO(ProxyConnection connection) {
        super(connection);
    }

    @Override
    public List findAll() throws DAOException {
        return null;
    }

    public List<Track> findAllInOrder(String order) throws DAOException {
        List<Track> tracks = new ArrayList<>();
        PreparedStatement statement = null;
        try {
            String select = SQL_SELECT_ALL_IN_ORDER + order;
            statement = connection.prepareStatement(select);
            ResultSet set = statement.executeQuery();
            tracks = takeTracks(set);
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            closeStatement(statement);
        }
        return tracks;
    }

    public List<Track> findTracksByGenre(String genre) throws DAOException{
        List<Track> tracks = new ArrayList<>();
        PreparedStatement statement = null;
        try{
            statement = connection.prepareStatement(SQL_SELECT_ALL_IN_GENRE);
            statement.setString(1,genre);
            ResultSet set = statement.executeQuery();
            tracks = takeTracks(set);
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            closeStatement(statement);
        }
        return tracks;
    }
}
