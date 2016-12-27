package by.liudchyk.audiotracks.logic;

import by.liudchyk.audiotracks.dao.UserDAO;
import by.liudchyk.audiotracks.database.ConnectionPool;
import by.liudchyk.audiotracks.database.ProxyConnection;
import by.liudchyk.audiotracks.entity.User;
import by.liudchyk.audiotracks.exception.DAOException;
import by.liudchyk.audiotracks.exception.LogicException;
import by.liudchyk.audiotracks.validator.Validator;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.jws.soap.SOAPBinding;
import java.sql.SQLException;

/**
 * Created by Admin on 24.12.2016.
 */
public class UserLogic {
    private static final Logger LOG = LogManager.getLogger();
    private final String MESSAGE = "message.success.register";
    private final String DEFAULT_MSG = "message.error.register";

    public String registerUser(String name, String email, String password, String confPass, String card) throws LogicException {
        Validator validator = new Validator();
        String msg = validator.isRegisterFormValid(name,password,confPass,card,email);
        if(!msg.isEmpty()){
            return msg;
        }
        ConnectionPool pool = ConnectionPool.getInstance();
        ProxyConnection connection = pool.getConnection();
        UserDAO userDAO = new UserDAO(connection);
        String md5Pass = DigestUtils.md5Hex(password);
        try {
            if(userDAO.addUser(name, email, md5Pass, card)){
                return MESSAGE;
            }else{
                return DEFAULT_MSG;
            }
        } catch (DAOException e) {
            throw new LogicException("Mistake in adding user", e);
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                LOG.warn("Connection can't be returned into pull",e);
            }
        }
    }

    public User findUserByLogin(String login) throws LogicException {
        ConnectionPool pool = ConnectionPool.getInstance();
        ProxyConnection connection = pool.getConnection();
        UserDAO userDAO = new UserDAO(connection);
        try {
            return userDAO.findUser(login);
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

    public User changeUserLogin(String newLogin, int id) throws LogicException {
        ConnectionPool pool = ConnectionPool.getInstance();
        ProxyConnection connection = pool.getConnection();
        UserDAO userDAO = new UserDAO(connection);
        try {
            return userDAO.changeLoginById(newLogin, id);
        } catch (DAOException e) {
            throw new LogicException(e);
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                LOG.warn("Connection can't be returned into pull",e);
            }
        }
    }
}
