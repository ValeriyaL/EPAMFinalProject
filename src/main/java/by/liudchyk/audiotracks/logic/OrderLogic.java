package by.liudchyk.audiotracks.logic;

import by.liudchyk.audiotracks.dao.OrderDAO;
import by.liudchyk.audiotracks.dao.TrackDAO;
import by.liudchyk.audiotracks.database.ConnectionPool;
import by.liudchyk.audiotracks.database.ProxyConnection;
import by.liudchyk.audiotracks.entity.Track;
import by.liudchyk.audiotracks.entity.User;
import by.liudchyk.audiotracks.exception.DAOException;
import by.liudchyk.audiotracks.exception.LogicException;

import java.util.ArrayList;

/**
 * Created by Admin on 04.01.2017.
 */
public class OrderLogic {
    public ArrayList<Track> findAllUserOrders(User user) throws LogicException{
        ProxyConnection connection = ConnectionPool.getInstance().getConnection();
        OrderDAO orderDAO = new OrderDAO(connection);
        try {
            return (ArrayList<Track>) orderDAO.findOrdersByUser(user);
        } catch (DAOException e) {
            throw new LogicException("Can't find all orders by user", e);
        } finally {
            orderDAO.closeConnection(connection);
        }
    }
}
