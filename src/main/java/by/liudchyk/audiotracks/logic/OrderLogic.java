package by.liudchyk.audiotracks.logic;

import by.liudchyk.audiotracks.dao.OrderDAO;
import by.liudchyk.audiotracks.dao.UserDAO;
import by.liudchyk.audiotracks.database.ConnectionPool;
import by.liudchyk.audiotracks.database.ProxyConnection;
import by.liudchyk.audiotracks.entity.Track;
import by.liudchyk.audiotracks.entity.User;
import by.liudchyk.audiotracks.exception.DAOException;
import by.liudchyk.audiotracks.exception.LogicException;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Admin on 04.01.2017.
 */
public class OrderLogic {
    public ArrayList<Track> findAllUserOrders(User user) throws LogicException {
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

    public boolean isOrderExist(int userId, int trackId) throws LogicException {
        ProxyConnection connection = ConnectionPool.getInstance().getConnection();
        OrderDAO orderDAO = new OrderDAO(connection);
        try {
            return orderDAO.isOrderExist(userId, trackId);
        } catch (DAOException e) {
            throw new LogicException("Can't find is order exist", e);
        } finally {
            orderDAO.closeConnection(connection);
        }
    }

    public boolean isBuyingValid(double price, int userId) throws LogicException {
        ProxyConnection connection = ConnectionPool.getInstance().getConnection();
        UserDAO userDAO = new UserDAO(connection);
        try {
            return userDAO.findMoneyById(userId)>=price;
        } catch (DAOException e) {
            throw new LogicException("Can't find is buying valid", e);
        } finally {
            userDAO.closeConnection(connection);
        }
    }

    public void addOrder(int trackId,double price, int userId,String formatDate) throws LogicException {
        ProxyConnection connection = ConnectionPool.getInstance().getConnection();
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
        }catch (SQLException e){
            throw new LogicException("Can't add order", e);
        }
    }
}
