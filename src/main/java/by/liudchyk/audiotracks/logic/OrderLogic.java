package by.liudchyk.audiotracks.logic;

import by.liudchyk.audiotracks.dao.OrderDAO;
import by.liudchyk.audiotracks.dao.UserDAO;
import by.liudchyk.audiotracks.database.ConnectionPool;
import by.liudchyk.audiotracks.database.ProxyConnection;
import by.liudchyk.audiotracks.entity.Track;
import by.liudchyk.audiotracks.entity.User;
import by.liudchyk.audiotracks.exception.DAOException;
import by.liudchyk.audiotracks.exception.LogicException;
import by.liudchyk.audiotracks.validator.Validator;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Class {@code OrderLogic} is a service class used to connect commands
 * with OrdersDAO.
 *
 * @author Liudchyk Valeriya
 */
public class OrderLogic {
    /**
     * Transfer to OrderDAO to find all user's Tracks ordered.
     *
     * @param user is User
     * @return ArrayList of Tracks ordered by user
     * @throws LogicException if OrderDAO throws DAOException
     */
    public ArrayList<Track> findAllUserOrders(User user) throws LogicException {
        ProxyConnection connection = ConnectionPool.getInstance().takeConnection();
        OrderDAO orderDAO = new OrderDAO(connection);
        try {
            return (ArrayList<Track>) orderDAO.findOrdersByUser(user);
        } catch (DAOException e) {
            throw new LogicException("Can't find all orders by user", e);
        } finally {
            orderDAO.closeConnection(connection);
        }
    }

    /**
     * Transfer to OrderDAO to check is order exist.
     *
     * @param userId  is id of user
     * @param trackId is id of track
     * @return the same value as isOrderExist in orderDAO
     * @throws LogicException if orderDAO throws DAOException
     */
    public boolean isOrderExist(int userId, int trackId) throws LogicException {
        ProxyConnection connection = ConnectionPool.getInstance().takeConnection();
        OrderDAO orderDAO = new OrderDAO(connection);
        try {
            return orderDAO.isOrderExist(userId, trackId);
        } catch (DAOException e) {
            throw new LogicException("Can't find is order exist", e);
        } finally {
            orderDAO.closeConnection(connection);
        }
    }

    /**
     * Checks is buying valid.
     *
     * @param price  is price of order
     * @param userId is user's id
     * @return true if user's money enough to pay order
     * false otherwise
     * @throws LogicException if userDAO throws DAOException
     */
    public boolean isBuyingValid(double price, int userId) throws LogicException {
        ProxyConnection connection = ConnectionPool.getInstance().takeConnection();
        UserDAO userDAO = new UserDAO(connection);
        try {
            return userDAO.findMoneyById(userId) >= price;
        } catch (DAOException e) {
            throw new LogicException("Can't find is buying valid", e);
        } finally {
            userDAO.closeConnection(connection);
        }
    }

    /**
     * Transaction. Adds track in ordered and takes money from user's account
     *
     * @param trackId    is ordered track's id
     * @param price      is price of order
     * @param userId     is id of user ordered track
     * @param formatDate is date of order in string format
     * @throws LogicException if UserDAO or OrderDAO throws DAOException
     */
    public void addOrder(int trackId, double price, int userId, String formatDate) throws LogicException {
        ProxyConnection connection = ConnectionPool.getInstance().takeConnection();
        OrderDAO orderDAO = new OrderDAO(connection);
        UserDAO userDAO = new UserDAO(connection);
        try {
            try {
                connection.setAutoCommit(false);
                double money = userDAO.findMoneyById(userId);
                userDAO.changeMoneyById(money - price, userId);
                orderDAO.addOrder(trackId, price, userId, formatDate);
                connection.commit();
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                throw new LogicException("Can't add order", e);
            } catch (DAOException e) {
                connection.rollback();
                connection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            throw new LogicException("Can't add order", e);
        } finally {
            orderDAO.closeConnection(connection);
        }
    }
}
