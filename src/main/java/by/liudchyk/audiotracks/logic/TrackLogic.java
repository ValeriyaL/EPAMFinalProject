package by.liudchyk.audiotracks.logic;

import by.liudchyk.audiotracks.dao.OrderDAO;
import by.liudchyk.audiotracks.database.ConnectionPool;
import by.liudchyk.audiotracks.database.ProxyConnection;
import by.liudchyk.audiotracks.entity.Track;
import by.liudchyk.audiotracks.exception.DAOException;
import by.liudchyk.audiotracks.exception.LogicException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Admin on 28.12.2016.
 */
public class TrackLogic {
    private static final Logger LOG = LogManager.getLogger();

    public ArrayList<Track> findLastOrders(int size) throws LogicException{
        ProxyConnection connection = ConnectionPool.getInstance().getConnection();
        OrderDAO orderDAO = new OrderDAO(connection);
        try {
            return (ArrayList<Track>) orderDAO.findLastOrders(size);
        } catch (DAOException e) {
            throw new LogicException(e);
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                LOG.warn("Connection can't be returned into pull",e);
            }
        }

    }
}
