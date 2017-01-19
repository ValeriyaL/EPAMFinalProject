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

import java.util.*;

/**
 * Created by Admin on 28.12.2016.
 */
public class TrackLogic {
    private final String INCORRECT_PRICE_MSG = "message.error.price";
    private final String MESSAGE = "message.success.add";
    private final String DEFAULT_MSG = "message.error.track.add";
    private final double PERCENTS = 100.0;

    public ArrayList<Track> findMostOrdered() throws LogicException {
        ProxyConnection connection = ConnectionPool.getInstance().getConnection();
        OrderDAO orderDAO = new OrderDAO(connection);
        try {
            return (ArrayList<Track>) orderDAO.findMostOrdered();
        } catch (DAOException e) {
            throw new LogicException("Can't find most ordered", e);
        } finally {
            orderDAO.closeConnection(connection);
        }
    }

    public ArrayList<Track> findAllTracksInOrder(String order) throws LogicException {
        ProxyConnection connection = ConnectionPool.getInstance().getConnection();
        TrackDAO orderDAO = new TrackDAO(connection);
        try {
            return (ArrayList<Track>) orderDAO.findAllInOrder(order);
        } catch (DAOException e) {
            throw new LogicException("Can't find all tracks in order", e);
        } finally {
            orderDAO.closeConnection(connection);
        }
    }

    public ArrayList<Track> findTracksByGenre(String genre) throws LogicException {
        ProxyConnection connection = ConnectionPool.getInstance().getConnection();
        TrackDAO trackDAO = new TrackDAO(connection);
        try {
            return (ArrayList<Track>) trackDAO.findTracksByGenre(genre);
        } catch (DAOException e) {
            throw new LogicException("Can't find all tracks by genre", e);
        } finally {
            trackDAO.closeConnection(connection);
        }
    }

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

    public ArrayList<Comment> findAllCommentsById(int id) throws LogicException {
        ProxyConnection connection = ConnectionPool.getInstance().getConnection();
        TrackDAO trackDAO = new TrackDAO(connection);
        try {
            return (ArrayList<Comment>) trackDAO.findCommentsByTrackId(id);
        } catch (DAOException e) {
            throw new LogicException("Can't find all comments by track id", e);
        } finally {
            trackDAO.closeConnection(connection);
        }
    }

    public Track findTrackById(int id) throws LogicException {
        ProxyConnection connection = ConnectionPool.getInstance().getConnection();
        TrackDAO trackDAO = new TrackDAO(connection);
        try {
            return trackDAO.findTrackById(id);
        } catch (DAOException e) {
            throw new LogicException("Can't find track by id", e);
        } finally {
            trackDAO.closeConnection(connection);
        }
    }

    public void deleteTrackById(int id) throws LogicException {
        ProxyConnection connection = ConnectionPool.getInstance().getConnection();
        TrackDAO trackDAO = new TrackDAO(connection);
        try {
            trackDAO.deleteTrackById(id);
        } catch (DAOException e) {
            throw new LogicException("Can't delete track by id", e);
        } finally {
            trackDAO.closeConnection(connection);
        }
    }

    public void recoverTrackById(int id) throws LogicException {
        ProxyConnection connection = ConnectionPool.getInstance().getConnection();
        TrackDAO trackDAO = new TrackDAO(connection);
        try {
            trackDAO.recoverTrackById(id);
        } catch (DAOException e) {
            throw new LogicException("Can't recover track by id", e);
        } finally {
            trackDAO.closeConnection(connection);
        }
    }

    public ArrayList<Track> findAllDeletedTracks() throws LogicException {
        ProxyConnection connection = ConnectionPool.getInstance().getConnection();
        TrackDAO trackDAO = new TrackDAO(connection);
        try {
            return (ArrayList<Track>) trackDAO.findAllDeletedTracks();
        } catch (DAOException e) {
            throw new LogicException("Can't find all deleted tracks", e);
        } finally {
            trackDAO.closeConnection(connection);
        }
    }

    public String changeTrackPrice(String newPrice, int trackId) throws LogicException {
        Validator validator = new Validator();
        String msg = validator.isPriceChangeValid(newPrice);
        if (!msg.isEmpty()) {
            return msg;
        }
        Double price = Double.valueOf(newPrice);
        ProxyConnection connection = ConnectionPool.getInstance().getConnection();
        TrackDAO trackDAO = new TrackDAO(connection);
        try {
            if (trackDAO.changeTrackPriceById(price, trackId)) {
                return "";
            } else {
                return INCORRECT_PRICE_MSG;
            }
        } catch (DAOException e) {
            throw new LogicException("Can't change track price", e);
        } finally {
            trackDAO.closeConnection(connection);
        }
    }

    public String findPathByTrackId(int id) throws LogicException {
        ProxyConnection connection = ConnectionPool.getInstance().getConnection();
        TrackDAO trackDAO = new TrackDAO(connection);
        try {
            return trackDAO.findTrackPathById(id);
        } catch (DAOException e) {
            throw new LogicException("Can't find track path by id", e);
        } finally {
            trackDAO.closeConnection(connection);
        }
    }

    public String addTrack(String title, String artist, String genre, String price, String link, String length) throws LogicException {
        Validator validator = new Validator();
        String msg = validator.isAddTrackValid(title,artist,genre,price,length);
        if (!msg.isEmpty()) {
            return msg;
        }
        ProxyConnection connection = ConnectionPool.getInstance().getConnection();
        TrackDAO trackDAO = new TrackDAO(connection);
        try {
            int genreId = 0;
            if(!genre.isEmpty()) {
               genreId = trackDAO.findGenreIdByGenreName(genre);
            }
            if (trackDAO.addTrack(title,artist,genreId,price,link,length)) {
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

    public double calculateBonusPrice(double price,int bonus){
        return price-(price*bonus/PERCENTS);
    }
}
