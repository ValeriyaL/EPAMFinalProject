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
 * Class {@code TrackDAO} is used to connect with data base.
 * Does all actions connected with tracks.
 *
 * @author Liudchyk Valeriya
 * @see AbstractDAO
 */
@SuppressWarnings("Duplicates")
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
    private static final String SQL_CHANGE_LENGTH = "UPDATE tracks SET length=? WHERE id=?";
    private static final String SQL_CHANGE_TITLE = "UPDATE tracks SET title=? WHERE id=?";
    private static final String SQL_CHANGE_ARTIST = "UPDATE tracks SET artists=? WHERE id=?";
    private static final String SQL_CHANGE_GENRE = "UPDATE tracks SET genre_id=? WHERE id=?";
    private static final String SQL_SELECT_PATH_BY_ID = "SELECT tracks.link FROM tracks WHERE id=?";
    private static final String SQL_SELECT_GENRE_ID = "SELECT genres.id FROM genres WHERE genres.name=?;";
    private static final String SQL_ADD_GENRE = "INSERT INTO genres(name) VALUES(?);";
    private static final String SQL_ADD_TRACK = "INSERT INTO tracks(title,artists, genre_id, price, length, link) VALUES(?,?,?,?,?,?)";
    private static final String SQL_ADD_TRACK_WITHOUT_GENRE = "INSERT INTO tracks(title,artists, price, length, link) VALUES(?,?,?,?,?)";

    public TrackDAO(ProxyConnection connection) {
        super(connection);
    }

    /**
     * Find all tracks in some order
     *
     * @param order is alphabet or price order
     * @return list of tracks
     * @throws DAOException if SQLException was thrown
     */
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

    /**
     * Finds all tracks by genre
     *
     * @param genre is track's genre
     * @return list of tracks
     * @throws DAOException if SQLException was thrown
     */
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

    /**
     * Finds all comments by track's id
     *
     * @param id is track's id
     * @return list of track's comments
     * @throws DAOException if SQLException was thrown
     */
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

    /**
     * Finds track by track's id
     *
     * @param id is track's id
     * @return Track object
     * @throws DAOException if SQLException was thrown
     */
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

    /**
     * Deletes track by track's id
     *
     * @param id is track's id
     * @throws DAOException if SQLException was thrown
     */
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

    /**
     * Recovers track by track's id
     *
     * @param id is track's id
     * @throws DAOException if SQLException was thrown
     */
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

    /**
     * Changes track's price by id
     *
     * @param newPrice is track's new price
     * @param trackId  is track's id
     * @return true if price was changed, false otherwise
     */
    public boolean changeTrackPriceById(double newPrice, int trackId) {
        boolean isAdded = false;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_CHANGE_PRICE);
            statement.setDouble(1, newPrice);
            statement.setString(2, Integer.toString(trackId));
            int i = statement.executeUpdate();
            if (i != 0) {
                isAdded = true;
            }
        } catch (SQLException e) {
            LOG.warn("SQLException in changeTrackPriceById", e);
        } finally {
            closeStatement(statement);
        }
        return isAdded;
    }

    /**
     * Changes track's length by id
     *
     * @param length  is new track's length
     * @param trackId is track's id
     * @return true if length was changed, false otherwise
     */
    public boolean changeTrackLengthById(int length, int trackId) {
        boolean isAdded = false;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_CHANGE_LENGTH);
            statement.setString(2, Integer.toString(trackId));
            statement.setDouble(1, length);
            int i = statement.executeUpdate();
            if (i != 0) {
                isAdded = true;
            }
        } catch (SQLException e) {
            LOG.warn("SQLException in changeTrackLengthById", e);
        } finally {
            closeStatement(statement);
        }
        return isAdded;
    }

    /**
     * Changes track's title by id
     *
     * @param newTitle is new track's title
     * @param trackId  is track's id
     * @return true if title was changed, false otherwise
     */
    public boolean changeTrackTitleById(String newTitle, int trackId) {
        boolean isAdded = false;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_CHANGE_TITLE);
            statement.setString(2, Integer.toString(trackId));
            statement.setString(1, newTitle);
            int i = statement.executeUpdate();
            if (i != 0) {
                isAdded = true;
            }
        } catch (SQLException e) {
            LOG.warn("SQLException in changeTrackTitleById", e);
        } finally {
            closeStatement(statement);
        }
        return isAdded;
    }

    /**
     * Changes track's artist by id
     *
     * @param newArtist is new track's artist
     * @param trackId   is track's id
     * @return true if artist was changed, false otherwise
     */
    public boolean changeTrackArtistById(String newArtist, int trackId) {
        boolean isAdded = false;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_CHANGE_ARTIST);
            statement.setString(1, newArtist);
            statement.setString(2, Integer.toString(trackId));
            int i = statement.executeUpdate();
            if (i != 0) {
                isAdded = true;
            }
        } catch (SQLException e) {
            LOG.warn("SQLException in changeTrackArtistById", e);
        } finally {
            closeStatement(statement);
        }
        return isAdded;
    }

    /**
     * Changes track's genre by it's id
     *
     * @param genreId is track's genre id
     * @param trackId is track's id
     * @return true if track's genre was changed, false otherwise
     */
    public boolean changeTrackGenreById(int genreId, int trackId) {
        boolean isAdded = false;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_CHANGE_GENRE);
            if (genreId != 0) {
                statement.setString(1, Integer.toString(genreId));
            } else {
                statement.setString(1, null);
            }
            statement.setString(2, Integer.toString(trackId));
            int i = statement.executeUpdate();
            if (i != 0) {
                isAdded = true;
            }
        } catch (SQLException e) {
            LOG.warn("SQLException in changeTrackGenreById", e);
        } finally {
            closeStatement(statement);
        }
        return isAdded;
    }

    /**
     * Finds all deleted tracks
     *
     * @return list of tracks
     * @throws DAOException if SQLException was thrown
     */
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

    /**
     * Finds path to track by id
     *
     * @param id is track's id
     * @return track's path
     * @throws DAOException if SQLException was thrown
     */
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

    /**
     * Finds genre id by genre's name
     *
     * @param genre is genre's name
     * @return genre's id
     * @throws DAOException if SQLException was thrown
     */
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

    /**
     * Adds genre to database if it doesn't exist
     *
     * @param genre is genre's name
     * @throws DAOException ig SQLException was thrown
     */
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

    /**
     * Adds track to database
     *
     * @param title   is track's title
     * @param artist  is track's artist
     * @param genreId is track's genre id
     * @param price   is track's price
     * @param link    is track's path
     * @param length  is track's length
     * @return true if track was added, false otherwise
     */
    public boolean addTrack(String title, String artist, int genreId, String price, String link, String length) {
        boolean isAdded = false;
        PreparedStatement statement = null;
        try {
            if (genreId != 0) {
                statement = connection.prepareStatement(SQL_ADD_TRACK);
                statement.setString(1, title);
                statement.setString(2, artist);
                statement.setString(3, Integer.toString(genreId));
                statement.setString(4, price);
                statement.setString(5, length);
                statement.setString(6, link);
            } else {
                statement = connection.prepareStatement(SQL_ADD_TRACK_WITHOUT_GENRE);
                statement.setString(1, title);
                statement.setString(2, artist);
                statement.setString(3, price);
                statement.setString(4, length);
                statement.setString(5, link);
            }
            statement.executeUpdate();
            isAdded = true;
        } catch (SQLException e) {
            LOG.warn("SQLException in addTrack", e);
        } finally {
            closeStatement(statement);
        }
        return isAdded;
    }
}
