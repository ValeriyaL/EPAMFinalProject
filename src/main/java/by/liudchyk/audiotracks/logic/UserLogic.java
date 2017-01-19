package by.liudchyk.audiotracks.logic;

import by.liudchyk.audiotracks.dao.UserDAO;
import by.liudchyk.audiotracks.database.ConnectionPool;
import by.liudchyk.audiotracks.database.ProxyConnection;
import by.liudchyk.audiotracks.entity.User;
import by.liudchyk.audiotracks.exception.DAOException;
import by.liudchyk.audiotracks.exception.LogicException;
import by.liudchyk.audiotracks.servlet.SessionRequestContent;
import by.liudchyk.audiotracks.validator.Validator;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.jws.soap.SOAPBinding;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Admin on 24.12.2016.
 */
public class UserLogic {
    private final String MESSAGE = "message.success.register";
    private final String DEFAULT_MSG = "message.error.register";
    private final String SUCCESS_MESSAGE = "message.success.change.email";
    private final String ERROR_MESSAGE = "message.error.change.email";
    private final String SUCCESS_LOGIN_MESSAGE = "message.success.change.login";
    private final String ERROR_LOGIN_MESSAGE = "message.error.change.login";
    private final String SUCCESS_CARD_MESSAGE = "message.success.change.card";
    private final String ERROR_CARD_MESSAGE = "message.error.change.card";
    private final String SUCCESS_PASSWORD_MESSAGE = "message.success.change.password";
    private final String ERROR_PASSWORD_MESSAGE = "message.error.change.password";
    private final String SUCCESS_MONEY_MESSAGE = "message.success.change.money";
    private final String ERROR_MONEY_MESSAGE = "message.error.change.money";

    public String registerUser(String name, String email, String password, String confPass, String card) throws LogicException {
        Validator validator = new Validator();
        String msg = validator.isRegisterFormValid(name, password, confPass, card, email);
        if (!msg.isEmpty()) {
            return msg;
        }
        ProxyConnection connection = ConnectionPool.getInstance().getConnection();
        UserDAO userDAO = new UserDAO(connection);
        String md5Pass = DigestUtils.md5Hex(password);
        try {
            return userDAO.addUser(name, email, md5Pass, card) ? MESSAGE : DEFAULT_MSG;
        } catch (DAOException e) {
            throw new LogicException("Mistake in adding user", e);
        } finally {
            userDAO.closeConnection(connection);
        }
    }

    public String changeUserEmail(String newEmail, int id) throws LogicException {
        Validator validator = new Validator();
        String msg = validator.isEmailChangeValid(newEmail);
        if (!msg.isEmpty()) {
            return msg;
        }
        ProxyConnection connection = ConnectionPool.getInstance().getConnection();
        UserDAO userDAO = new UserDAO(connection);
        try {
            return userDAO.changeEmailById(newEmail, id) ? SUCCESS_MESSAGE : ERROR_MESSAGE;
        } catch (DAOException e) {
            throw new LogicException("Can't change user email", e);
        } finally {
            userDAO.closeConnection(connection);
        }
    }

    public String changeUserLogin(String newLogin, int id) throws LogicException {
        Validator validator = new Validator();
        String msg = validator.isLoginChangeValid(newLogin);
        if (!msg.isEmpty()) {
            return msg;
        }
        ProxyConnection connection = ConnectionPool.getInstance().getConnection();
        UserDAO userDAO = new UserDAO(connection);
        try {
            return userDAO.changeLoginById(newLogin, id) ? SUCCESS_LOGIN_MESSAGE : ERROR_LOGIN_MESSAGE;
        } catch (DAOException e) {
            throw new LogicException("Can't change user logic", e);
        } finally {
            userDAO.closeConnection(connection);
        }
    }

    public String changeUserPassword(String oldPassword, String newPassword, String newPasswordConf, int id) throws LogicException {
        Validator validator = new Validator();
        String msg = validator.isPasswordChangeValid(oldPassword, newPassword, newPasswordConf, id);
        if (!msg.isEmpty()) {
            return msg;
        }
        ProxyConnection connection = ConnectionPool.getInstance().getConnection();
        UserDAO userDAO = new UserDAO(connection);
        String md5Password = DigestUtils.md5Hex(newPassword);
        try {
            return userDAO.changePasswordById(md5Password, id) ? SUCCESS_PASSWORD_MESSAGE : ERROR_PASSWORD_MESSAGE;
        } catch (DAOException e) {
            throw new LogicException("Can't change user password", e);
        } finally {
            userDAO.closeConnection(connection);
        }
    }

    public String changeUserCard(String newCard, int id) throws LogicException {
        Validator validator = new Validator();
        String msg = validator.isCardChangeValid(newCard);
        if (!msg.isEmpty()) {
            return msg;
        }
        ProxyConnection connection = ConnectionPool.getInstance().getConnection();
        UserDAO userDAO = new UserDAO(connection);
        try {
            return userDAO.changeCardById(newCard, id) ? SUCCESS_CARD_MESSAGE : ERROR_CARD_MESSAGE;
        } catch (DAOException e) {
            throw new LogicException("Can't change user card", e);
        } finally {
            userDAO.closeConnection(connection);
        }
    }

    public String changeUserMoney(double money, int id, String card) throws LogicException {
        Validator validator = new Validator();
        String msg = validator.isMoneyChangeValid(money, card);
        if (!msg.isEmpty()) {
            return msg;
        }
        ProxyConnection connection = ConnectionPool.getInstance().getConnection();
        UserDAO userDAO = new UserDAO(connection);
        try {
            return userDAO.changeMoneyById(money, id) ? SUCCESS_MONEY_MESSAGE : ERROR_MONEY_MESSAGE;
        } catch (DAOException e) {
            throw new LogicException("Can't change user money", e);
        } finally {
            userDAO.closeConnection(connection);
        }
    }

    public User findUserByLogin(String login) throws LogicException {
        ProxyConnection connection = ConnectionPool.getInstance().getConnection();
        UserDAO userDAO = new UserDAO(connection);
        try {
            return userDAO.findUser(login);
        } catch (DAOException e) {
            throw new LogicException("Can't find user by id", e);
        } finally {
            userDAO.closeConnection(connection);
        }
    }

    public int findNumberOfCommentsById(int id) throws LogicException {
        ProxyConnection connection = ConnectionPool.getInstance().getConnection();
        UserDAO userDAO = new UserDAO(connection);
        try {
            return userDAO.commentsNumberById(id);
        } catch (DAOException e) {
            throw new LogicException("Can't find number of comments by id", e);
        } finally {
            userDAO.closeConnection(connection);
        }
    }

    public int changeBonusByNickname(String nickname, String bonus) throws LogicException {
        Validator validator = new Validator();
        if (!validator.isBonusValid(bonus)) {
            return -1;
        }
        ProxyConnection connection = ConnectionPool.getInstance().getConnection();
        UserDAO userDAO = new UserDAO(connection);
        try {
            return userDAO.changeBonusByNickname(nickname, bonus);
        } catch (DAOException e) {
            throw new LogicException("Can't change bonus by nickname", e);
        } finally {
            userDAO.closeConnection(connection);
        }
    }

    public int findBonusById(int id) throws LogicException {
        ProxyConnection connection = ConnectionPool.getInstance().getConnection();
        UserDAO userDAO = new UserDAO(connection);
        try {
            return userDAO.findBonusById(id);
        } catch (DAOException e) {
            throw new LogicException("Can't find bonus by id", e);
        } finally {
            userDAO.closeConnection(connection);
        }
    }

    public double findMoneyById(int id) throws LogicException {
        ProxyConnection connection = ConnectionPool.getInstance().getConnection();
        UserDAO userDAO = new UserDAO(connection);
        try {
            return userDAO.findMoneyById(id);
        } catch (DAOException e) {
            throw new LogicException("Can't find money by id", e);
        } finally {
            userDAO.closeConnection(connection);
        }
    }

    public ArrayList<User> findAllUsers() throws LogicException {
        ProxyConnection connection = ConnectionPool.getInstance().getConnection();
        UserDAO userDAO = new UserDAO(connection);
        try {
            return userDAO.findAllUsers();
        } catch (DAOException e) {
            throw new LogicException("Can't find all users", e);
        } finally {
            userDAO.closeConnection(connection);
        }
    }
}
