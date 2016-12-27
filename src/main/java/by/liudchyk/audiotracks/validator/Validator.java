package by.liudchyk.audiotracks.validator;

import by.liudchyk.audiotracks.dao.UserDAO;
import by.liudchyk.audiotracks.database.ConnectionPool;
import by.liudchyk.audiotracks.database.ProxyConnection;
import by.liudchyk.audiotracks.entity.User;
import by.liudchyk.audiotracks.exception.DAOException;
import by.liudchyk.audiotracks.exception.LogicException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Admin on 24.12.2016.
 */
public class Validator {
    private static final Logger LOG = LogManager.getLogger();
    private final String EMAIL_REGEXP = ".+@[a-z]{2,6}\\.[a-z]{2,4}";
    private final String LOGIN_MSG = "message.lengthLogin";
    private final String PASSWORD_MSG = "message.lengthPass";
    private final String EMAIL_MSG = "message.email";
    private final String CARD_MSG = "message.lengthCard";
    private final String CONFIRM_MSG = "message.confirm";
    private final String LOGIN_UNIQUE_MSG = "message.login.unique";
    private final String EMAIL_UNIQUE_MSG = "message.email.unique";
    private final String CARD_UNIQUE_MSG = "message.card.unique";

    public String isRegisterFormValid(String name, String password, String confirm, String card, String email) throws LogicException{
        String res = "";
        if(!isLoginLengthValid(name)){
            return LOGIN_MSG;
        }
        if(!isPasswordLengthValid(password)){
            return PASSWORD_MSG;
        }
        if(!isEmailValid(email)){
            return EMAIL_MSG;
        }
        if(!isCardValid(card)){
            return CARD_MSG;
        }
        if(!isConfirmPasswordValid(password,confirm)){
            return CONFIRM_MSG;
        }
        if(!isLoginUnique(name)){
            return LOGIN_UNIQUE_MSG;
        }
        if(!isEmailUnique(email)){
            return EMAIL_UNIQUE_MSG;
        }
        if(!isCardUnique(card)){
            return CARD_UNIQUE_MSG;
        }
        return res;
    }

    public boolean isLoginLengthValid(String login) {
        if (login.length() > 3 && login.length() < 15) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isPasswordLengthValid(String password) {
        if (password.length() > 4 && password.length() < 20) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isEmailValid(String email) {
        Pattern pattern = Pattern.compile(EMAIL_REGEXP);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public boolean isConfirmPasswordValid(String password, String confPass) {
        return password.equals(confPass);
    }

    public boolean isCardValid(String card) {
        if (card.isEmpty() || (card.length() >= 13 && card.length() <= 19)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isLoginUnique(String login) throws LogicException{
        ConnectionPool pool = ConnectionPool.getInstance();
        ProxyConnection connection = pool.getConnection();
        UserDAO userDAO = new UserDAO(connection);
        try {
            if (userDAO.findUser(login) == null) {
                return true;
            } else {
                return false;
            }
        } catch (DAOException e) {
            throw new LogicException(e);
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                LOG.warn("Connection can't be returned into pull", e);
            }
        }
    }

    public boolean isEmailUnique(String email) throws LogicException{
        ConnectionPool pool = ConnectionPool.getInstance();
        ProxyConnection connection = pool.getConnection();
        UserDAO userDAO = new UserDAO(connection);
        try {
            if (userDAO.findUserByEmail(email) == null) {
                return true;
            } else {
                return false;
            }
        } catch (DAOException e) {
            throw new LogicException(e);
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                LOG.warn("Connection can't be returned into pull", e);
            }
        }
    }

    public boolean isCardUnique(String card) throws LogicException{
        ConnectionPool pool = ConnectionPool.getInstance();
        ProxyConnection connection = pool.getConnection();
        UserDAO userDAO = new UserDAO(connection);
        try {
            if (userDAO.findUserByCard(card) == null) {
                return true;
            } else {
                return false;
            }
        } catch (DAOException e) {
            throw new LogicException(e);
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                LOG.warn("Connection can't be returned into pull", e);
            }
        }
    }
}
