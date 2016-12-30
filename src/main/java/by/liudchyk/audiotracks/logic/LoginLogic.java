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

import java.sql.SQLException;

/**
 * Created by Admin on 24.12.2016.
 */
public class LoginLogic {
    private static final Logger LOG = LogManager.getLogger();

    public boolean checkLogin(String login, String password) throws LogicException {
        Validator validator = new Validator();
        if (!validator.isLoginLengthValid(login) || !validator.isPasswordLengthValid(password)) {
            return false;
        }
        ProxyConnection connection = ConnectionPool.getInstance().getConnection();
        UserDAO userDAO = new UserDAO(connection);
        String md5Pass = DigestUtils.md5Hex(password);
        try {
            String dbPass = userDAO.findPasswordForLogin(login);
            if (md5Pass.equals(dbPass)) {
                return true;
            }
        } catch (DAOException e) {
            throw new LogicException("Mistake in checking login", e);
        } finally {
            userDAO.closeConnection(connection);
        }
        return false;
    }
}
