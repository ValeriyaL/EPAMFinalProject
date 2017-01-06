package by.liudchyk.audiotracks.dao;

import by.liudchyk.audiotracks.database.ProxyConnection;
import by.liudchyk.audiotracks.entity.Comment;
import by.liudchyk.audiotracks.entity.Track;
import by.liudchyk.audiotracks.exception.DAOException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 01.01.2017.
 */
public class TrackDAO extends AbstractDAO {
    private final String MUSIC_PATH = "D:\\music\\";
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
    private static final String SQL_SELECT_TRACK_COMMENTS = "SELECT comments.text, comments.date, users.nickname\n" +
            "FROM comments\n" +
            "JOIN users ON users.id = comments.user_id\n" +
            "WHERE comments.track_id=?";
    private static final String SQL_SELECT_TRACK_BY_ID = "SELECT tracks.id, tracks.title, genres.name AS genre, tracks.artists, tracks.price, tracks.length\n" +
            "FROM tracks\n" +
            "LEFT JOIN genres ON tracks.genre_id=genres.id\n" +
            "WHERE tracks.id=?";
    private static final String SQL_DELETE_TRACK = "UPDATE tracks SET visible=0 WHERE id=?";
    private static final String SQL_SELECT_DELETED_TRACKS = "SELECT tracks.id, tracks.title, genres.name AS genre, tracks.artists, tracks.price, tracks.length\n" +
            "FROM tracks\n" +
            "LEFT JOIN genres ON tracks.genre_id=genres.id\n" +
            "WHERE visible=0";
    private static final String SQL_RECOVER_TRACK = "UPDATE tracks SET visible=1 WHERE id=?";
    private static final String SQL_CHANGE_PRICE = "UPDATE tracks SET price=? WHERE id=?";
    private static final String SQL_SELECT_PATH_BY_ID = "SELECT tracks.link FROM tracks WHERE id=?";
    private static final String SQL_SELECT_GENRE_ID = "SELECT genres.id FROM genres WHERE genres.name=?;";
    private static final String SQL_ADD_GENRE = "INSERT INTO genres(name) VALUES(?);";
    private static final String SQL_ADD_TRACK = "INSERT INTO tracks(title,artists, genre_id, price, length, link) VALUES(?,?,?,?,?,?)";
    private static final String SQL_ADD_TRACK_WITHOUT_GENRE = "INSERT INTO tracks(title,artists, price, length, link) VALUES(?,?,?,?,?)";

    public TrackDAO(ProxyConnection connection) {
        super(connection);
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

    public List<Track> findTracksByGenre(String genre) throws DAOException {
        List<Track> tracks = new ArrayList<>();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_SELECT_ALL_IN_GENRE);
            statement.setString(1, genre);
            ResultSet set = statement.executeQuery();
            tracks = takeTracks(set);
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            closeStatement(statement);
        }
        return tracks;
    }

    public List<Comment> findCommentsByTrackId(int id) throws DAOException {
        List<Comment> comments = new ArrayList<>();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_SELECT_TRACK_COMMENTS);
            statement.setString(1, Integer.toString(id));
            ResultSet set = statement.executeQuery();
            while (set.next()) {
                String text = set.getString(1);
                String user = set.getString(3);
                String date = set.getString(2);
                comments.add(new Comment(text, user, date));
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            closeStatement(statement);
        }
        return comments;
    }

    public Track findTrackById(int id) throws DAOException {
        Track track;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_SELECT_TRACK_BY_ID);
            statement.setString(1, Integer.toString(id));
            ResultSet set = statement.executeQuery();
            track = (Track) takeTracks(set).get(0);
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            closeStatement(statement);
        }
        return track;
    }

    public void deleteTrackById(int id) throws DAOException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_DELETE_TRACK);
            statement.setString(1, Integer.toString(id));
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            closeStatement(statement);
        }
    }

    public void recoverTrackById(int id) throws DAOException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_RECOVER_TRACK);
            statement.setString(1, Integer.toString(id));
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            closeStatement(statement);
        }
    }

    public boolean changeTrackPriceById(double newPrice, int trackId) throws DAOException {
        boolean isAdded;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_CHANGE_PRICE);
            statement.setDouble(1, newPrice);
            statement.setString(2, Integer.toString(trackId));
            int i = statement.executeUpdate();
            if (i != 0) {
                isAdded = true;
            } else {
                isAdded = false;
            }
        } catch (SQLException e) {
            // throw new DAOException(e);
            isAdded = false;
        } finally {
            closeStatement(statement);
        }
        return isAdded;
    }

    public List<Track> findAllDeletedTracks() throws DAOException {
        List<Track> tracks = new ArrayList<>();
        Statement statement = null;
        try {
            statement = connection.createStatement();
            ResultSet set = statement.executeQuery(SQL_SELECT_DELETED_TRACKS);
            tracks = takeTracks(set);
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            closeStatement(statement);
        }
        return tracks;
    }

    public String findTrackPathById(int id) throws DAOException {
        String path = "";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_SELECT_PATH_BY_ID);
            statement.setString(1, Integer.toString(id));
            ResultSet set = statement.executeQuery();
            if (set.next()) {
                path = set.getString(1);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            closeStatement(statement);
        }
        return path;
    }

    public int findGenreIdByGenreName(String genre) throws DAOException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_SELECT_GENRE_ID);
            statement.setString(1, genre);
            ResultSet set = statement.executeQuery();
            if (set.next()) {
                return set.getInt(1);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            closeStatement(statement);
        }
        return 0;
    }

    public void addGenreIfNotExists(String genre) throws DAOException {
        PreparedStatement statement = null;
        try {
            int id = findGenreIdByGenreName(genre);
            if (id == 0) {
                statement = connection.prepareStatement(SQL_ADD_GENRE);
                statement.setString(1, genre);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            closeStatement(statement);
        }
    }

    public boolean addTrack(String title, String artist, int genreId, String price, String link, String length) {
        boolean isAdded;
        PreparedStatement statement = null;
        String realLink = MUSIC_PATH + link;
        try {
            if (genreId != 0) {
                statement = connection.prepareStatement(SQL_ADD_TRACK);
                statement.setString(1, title);
                statement.setString(2, artist);
                statement.setString(3, Integer.toString(genreId));
                statement.setString(4, price);
                statement.setString(5, length);
                statement.setString(6, realLink);
            } else {
                statement = connection.prepareStatement(SQL_ADD_TRACK_WITHOUT_GENRE);
                statement.setString(1, title);
                statement.setString(2, artist);
                statement.setString(3, price);
                statement.setString(4, length);
                statement.setString(5, realLink);
            }
            statement.executeUpdate();
            isAdded = true;
        } catch (SQLException e) {
            isAdded = false;
        } finally {
            closeStatement(statement);
        }
        return isAdded;
    }
}
