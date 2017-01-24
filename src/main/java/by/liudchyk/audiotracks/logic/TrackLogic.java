package by.liudchyk.audiotracks.logic;

import by.liudchyk.audiotracks.dao.OrderDAO;
import by.liudchyk.audiotracks.dao.TrackDAO;
import by.liudchyk.audiotracks.database.ConnectionPool;
import by.liudchyk.audiotracks.database.ProxyConnection;
import by.liudchyk.audiotracks.entity.Comment;
import by.liudchyk.audiotracks.entity.Track;
import by.liudchyk.audiotracks.exception.DAOException;
import by.liudchyk.audiotracks.exception.LogicException;
import by.liudchyk.audiotracks.validator.Validator;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

/**
 * Class {@code TrackLogic} is a service class used to connect commands
 * with TrackDAO and does some logic connected with tracks.
 *
 * @author Liudchyk Valeriya
 * @see Validator
 */
public class TrackLogic {
    private final String INCORRECT_PRICE_MSG = "message.error.price";
    private final String MESSAGE = "message.success.add";
    private final String DEFAULT_MSG = "message.error.track.add";
    private final String LENGTH_MSG = "message.error.length";
    private final String TITLE_LENGTH_MSG = "message.error.title";
    private final String TITLE_MSG = "message.error.change.title";
    private final String ARTIST_MSG = "message.error.artist";
    private final String ARTIST_ERROR_MSG = "message.error.change.artist";
    private final String GENRE_ERROR_MSG = "message.error.change.genre";
    private final double PERCENTS = 100.0;

    /**
     * Transfer to OrderDAO to find most ordered tracks.
     *
     * @return ArrayList of most ordered Tracks
     * @throws LogicException if OrderDAO throws DAOException
     */
    public ArrayList<Track> findMostOrdered() throws LogicException {
        ProxyConnection connection = ConnectionPool.getInstance().takeConnection();
        OrderDAO orderDAO = new OrderDAO(connection);
        try {
            return (ArrayList<Track>) orderDAO.findMostOrdered();
        } catch (DAOException e) {
            throw new LogicException("Can't find most ordered", e);
        } finally {
            orderDAO.closeConnection(connection);
        }
    }

    /**
     * Transfer to trackDAO to find all tracks in some order.
     *
     * @param order is order of tracks (alphabet, by price...)
     * @return ArrayList of all Tracks
     * @throws LogicException if trackDAO throws DAOException
     */
    public ArrayList<Track> findAllTracksInOrder(String order) throws LogicException {
        ProxyConnection connection = ConnectionPool.getInstance().takeConnection();
        TrackDAO trackDAO = new TrackDAO(connection);
        try {
            return (ArrayList<Track>) trackDAO.findAllInOrder(order);
        } catch (DAOException e) {
            throw new LogicException("Can't find all tracks in order", e);
        } finally {
            trackDAO.closeConnection(connection);
        }
    }

    /**
     * Transfer to trackDAO to find all tracks by some genre.
     *
     * @param genre is genre of tracks
     * @return ArrayList of Tracks with genre
     * @throws LogicException if trackDAO throws DAOException
     */
    public ArrayList<Track> findTracksByGenre(String genre) throws LogicException {
        ProxyConnection connection = ConnectionPool.getInstance().takeConnection();
        TrackDAO trackDAO = new TrackDAO(connection);
        try {
            return (ArrayList<Track>) trackDAO.findTracksByGenre(genre);
        } catch (DAOException e) {
            throw new LogicException("Can't find all tracks by genre", e);
        } finally {
            trackDAO.closeConnection(connection);
        }
    }

    /**
     * Divides all tracks into pages.
     *
     * @param pageSize is number of track on one page.
     * @param tracks   is tracks that should be divided
     * @return Map with number of page as a key and ArrayList of tracks as a value
     */
    public Map<Integer, ArrayList<Track>> divideIntoPages(int pageSize, ArrayList<Track> tracks) {
        if (tracks.size() < pageSize) {
            return Collections.emptyMap();
        }
        HashMap<Integer, ArrayList<Track>> res = new HashMap<>();
        int numOfPages = (tracks.size() % pageSize) == 0 ? tracks.size() / pageSize : tracks.size() / pageSize + 1;
        for (int i = 0; i < numOfPages; i++) {
            ArrayList<Track> temp = new ArrayList<>();
            for (int j = 0; j < pageSize; j++) {
                if (tracks.size() > i * pageSize + j) {
                    temp.add(tracks.get(i * pageSize + j));
                }
            }
            res.put(i + 1, temp);
        }
        return res;
    }

    /**
     * Finds tracks that have in a title or artist such substring.
     *
     * @param tracks    is list of tracks
     * @param substring is substring to find
     * @return ArrayList of appropriate Tracks
     */
    public ArrayList<Track> findAppropriate(ArrayList<Track> tracks, String substring) {
        ArrayList<Track> res = new ArrayList<>();
        for (Track temp : tracks) {
            if (temp.getTitle().toUpperCase().contains(substring.toUpperCase())) {
                res.add(temp);
            } else if (temp.getArtist().toUpperCase().contains(substring.toUpperCase())) {
                res.add(temp);
            }
        }
        return res;
    }

    /**
     * Transfers to trackDAO to find all comments to track with id.
     *
     * @param id is track's id
     * @return ArrayList of Comments to track
     * @throws LogicException id trackDAO throws DAOException
     */
    public ArrayList<Comment> findAllCommentsById(int id) throws LogicException {
        ProxyConnection connection = ConnectionPool.getInstance().takeConnection();
        TrackDAO trackDAO = new TrackDAO(connection);
        try {
            return (ArrayList<Comment>) trackDAO.findCommentsByTrackId(id);
        } catch (DAOException e) {
            throw new LogicException("Can't find all comments by track id", e);
        } finally {
            trackDAO.closeConnection(connection);
        }
    }

    /**
     * Transfer to trackDAO to find track by its id.
     *
     * @param id is track's id
     * @return Track with id
     * @throws LogicException if trackDAO throws DAOException
     */
    public Track findTrackById(int id) throws LogicException {
        ProxyConnection connection = ConnectionPool.getInstance().takeConnection();
        TrackDAO trackDAO = new TrackDAO(connection);
        try {
            return trackDAO.findTrackById(id);
        } catch (DAOException e) {
            throw new LogicException("Can't find track by id", e);
        } finally {
            trackDAO.closeConnection(connection);
        }
    }

    /**
     * Transfer to trackDAO to delete track by its id.
     *
     * @param id is track's id
     * @throws LogicException if trackDAO throws DAOException
     */
    public void deleteTrackById(int id) throws LogicException {
        ProxyConnection connection = ConnectionPool.getInstance().takeConnection();
        TrackDAO trackDAO = new TrackDAO(connection);
        try {
            trackDAO.deleteTrackById(id);
        } catch (DAOException e) {
            throw new LogicException("Can't delete track by id", e);
        } finally {
            trackDAO.closeConnection(connection);
        }
    }

    /**
     * Transfer to trackDAO to recover track by it's id.
     *
     * @param id id track's id
     * @throws LogicException if trackDAO throws DAOException
     */
    public void recoverTrackById(int id) throws LogicException {
        ProxyConnection connection = ConnectionPool.getInstance().takeConnection();
        TrackDAO trackDAO = new TrackDAO(connection);
        try {
            trackDAO.recoverTrackById(id);
        } catch (DAOException e) {
            throw new LogicException("Can't recover track by id", e);
        } finally {
            trackDAO.closeConnection(connection);
        }
    }

    /**
     * Transfer to trackDAO to find all deleted tracks.
     *
     * @return ArrayList of all deleted tracks
     * @throws LogicException if trackDAO throws DAOException
     */
    public ArrayList<Track> findAllDeletedTracks() throws LogicException {
        ProxyConnection connection = ConnectionPool.getInstance().takeConnection();
        TrackDAO trackDAO = new TrackDAO(connection);
        try {
            return (ArrayList<Track>) trackDAO.findAllDeletedTracks();
        } catch (DAOException e) {
            throw new LogicException("Can't find all deleted tracks", e);
        } finally {
            trackDAO.closeConnection(connection);
        }
    }

    /**
     * Validates new price of track. Transfer to trackDAO to change price
     *
     * @param newPrice is new track's price
     * @param trackId  is track's id
     * @return empty string if price changed,
     * string with message otherwise
     */
    public String changeTrackPrice(String newPrice, int trackId) {
        Validator validator = new Validator();
        String msg = validator.isPriceChangeValid(newPrice);
        if (!msg.isEmpty()) {
            return msg;
        }
        Double price = Double.valueOf(newPrice);
        ProxyConnection connection = ConnectionPool.getInstance().takeConnection();
        TrackDAO trackDAO = new TrackDAO(connection);
        try {
            return trackDAO.changeTrackPriceById(price, trackId) ? "" : INCORRECT_PRICE_MSG;
        } finally {
            trackDAO.closeConnection(connection);
        }
    }

    /**
     * Transfer to trackDAO to find track path by its id
     *
     * @param id is track's id
     * @return string with path to track
     * @throws LogicException if trackDAO throws DAOException
     */
    public String findPathByTrackId(int id) throws LogicException {
        ProxyConnection connection = ConnectionPool.getInstance().takeConnection();
        TrackDAO trackDAO = new TrackDAO(connection);
        try {
            return trackDAO.findTrackPathById(id);
        } catch (DAOException e) {
            throw new LogicException("Can't find track path by id", e);
        } finally {
            trackDAO.closeConnection(connection);
        }
    }

    /**
     * Validates all params. Transfer to trackDAO to add track
     *
     * @param title  is track's title
     * @param artist is track's artist
     * @param genre  is track's genre
     * @param price  is track's price
     * @param link   is track's link
     * @param length is track's length in seconds
     * @return string message about success or error adding
     * @throws LogicException if trackDAO throws DAOException
     */
    public String addTrack(String title, String artist, String genre, String price, String link, String length) throws LogicException {
        Validator validator = new Validator();
        String msg = validator.isAddTrackValid(title, artist, genre, price, length);
        if (!msg.isEmpty()) {
            return msg;
        }
        ProxyConnection connection = ConnectionPool.getInstance().takeConnection();
        TrackDAO trackDAO = new TrackDAO(connection);
        try {
            int genreId = 0;
            if (!genre.isEmpty()) {
                genreId = trackDAO.findGenreIdByGenreName(genre);
            }
            if (trackDAO.addTrack(title, artist, genreId, price, link, length)) {
                return MESSAGE;
            } else {
                return DEFAULT_MSG;
            }
        } catch (DAOException e) {
            throw new LogicException("Mistake in adding track", e);
        } finally {
            trackDAO.closeConnection(connection);
        }
    }

    /**
     * Calculates bonus for the price
     *
     * @param price is price of order
     * @param bonus is user's bonus
     * @return new price considering user's bonus
     */
    public double calculateBonusPrice(double price, int bonus) {
        return new BigDecimal(price - (price * bonus / PERCENTS)).setScale(3, RoundingMode.HALF_UP).doubleValue();
    }

    /**
     * Validate new track's length. Transfer to trackDAO to vhange length
     *
     * @param newLength is new track's length in seconds
     * @param trackId   is track's id
     * @return empty string if track length changed,
     * string with error message otherwise
     */
    public String changeTrackLength(String newLength, int trackId) {
        Validator validator = new Validator();
        if (!validator.isLengthValid(newLength)) {
            return LENGTH_MSG;
        }
        int length = Integer.valueOf(newLength);
        ProxyConnection connection = ConnectionPool.getInstance().takeConnection();
        TrackDAO trackDAO = new TrackDAO(connection);
        try {
            return trackDAO.changeTrackLengthById(length, trackId) ? "" : INCORRECT_PRICE_MSG;
        } finally {
            trackDAO.closeConnection(connection);
        }
    }

    /**
     * Validate new track's title. Transfer to trackDAO to change title
     *
     * @param newTitle is new track's title
     * @param trackId  is track's id
     * @return empty string if track length changed,
     * string with error message otherwise
     */
    public String changeTrackTitle(String newTitle, int trackId) {
        Validator validator = new Validator();
        if (!validator.isTitleLengthValid(newTitle)) {
            return TITLE_LENGTH_MSG;
        }
        ProxyConnection connection = ConnectionPool.getInstance().takeConnection();
        TrackDAO trackDAO = new TrackDAO(connection);
        try {
            return trackDAO.changeTrackTitleById(newTitle, trackId) ? "" : TITLE_MSG;
        } finally {
            trackDAO.closeConnection(connection);
        }
    }

    /**
     * Validate new track's artist. Transfer to trackDAO to change artist
     *
     * @param newArtist is new track's artist
     * @param trackId   is track's id
     * @return empty string if track length changed,
     * string with error message otherwise
     */
    public String changeTrackArtist(String newArtist, int trackId) {
        Validator validator = new Validator();
        if (!validator.isTitleLengthValid(newArtist)) {
            return ARTIST_MSG;
        }
        ProxyConnection connection = ConnectionPool.getInstance().takeConnection();
        TrackDAO trackDAO = new TrackDAO(connection);
        try {
            return trackDAO.changeTrackArtistById(newArtist, trackId) ? "" : ARTIST_ERROR_MSG;
        } finally {
            trackDAO.closeConnection(connection);
        }
    }

    /**
     * Validate new track's genre. Transfer to trackDAO to change genre
     *
     * @param newGenre is new track's genre
     * @param trackId  is track's id
     * @return empty string if track length changed,
     * string with error message otherwise
     * @throws LogicException if trackDAO throws DAOException
     */
    public String changeTrackGenre(String newGenre, int trackId) throws LogicException {
        Validator validator = new Validator();
        String msg;
        if (!(msg = validator.checkGenre(newGenre)).isEmpty()) {
            return msg;
        }
        ProxyConnection connection = ConnectionPool.getInstance().takeConnection();
        TrackDAO trackDAO = new TrackDAO(connection);
        try {
            int genreId = trackDAO.findGenreIdByGenreName(newGenre);
            return trackDAO.changeTrackGenreById(genreId, trackId) ? "" : GENRE_ERROR_MSG;
        } catch (DAOException e) {
            throw new LogicException("Can't account track genre", e);
        } finally {
            trackDAO.closeConnection(connection);
        }
    }
}
