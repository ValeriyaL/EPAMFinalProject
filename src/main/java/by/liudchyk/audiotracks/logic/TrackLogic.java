package by.liudchyk.audiotracks.logic;

import by.liudchyk.audiotracks.dao.OrderDAO;
import by.liudchyk.audiotracks.dao.TrackDAO;
import by.liudchyk.audiotracks.database.ConnectionPool;
import by.liudchyk.audiotracks.database.ProxyConnection;
import by.liudchyk.audiotracks.entity.Comment;
import by.liudchyk.audiotracks.entity.Track;
import by.liudchyk.audiotracks.exception.DAOException;
import by.liudchyk.audiotracks.exception.LogicException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.criteria.CriteriaBuilder;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by Admin on 28.12.2016.
 */
public class TrackLogic {
    private static final Logger LOG = LogManager.getLogger();

    public ArrayList<Track> findLastOrders() throws LogicException {
        ProxyConnection connection = ConnectionPool.getInstance().getConnection();
        OrderDAO orderDAO = new OrderDAO(connection);
        try {
            return (ArrayList<Track>) orderDAO.findLastOrders();
        } catch (DAOException e) {
            throw new LogicException("Can't find last orders", e);
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
            for(int j=0;j<pageSize;j++){
                if(tracks.size()>i*pageSize+j) {
                    temp.add(tracks.get(i * pageSize + j));
                }
            }
            res.put(i+1,temp);
        }
        return res;
    }

    public ArrayList<Track> findAppropriate(ArrayList<Track> tracks, String substring){
        ArrayList<Track> res = new ArrayList<>();
        for(Track temp: tracks){
            if(temp.getTitle().toUpperCase().contains(substring.toUpperCase())){
                res.add(temp);
            }else if(temp.getArtist().toUpperCase().contains(substring.toUpperCase())){
                res.add(temp);
            }
        }
        return res;
    }

    public ArrayList<Comment> findAllCommentsById(int id) throws LogicException{
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

    public Track findTrackById(int id) throws LogicException{
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
}
