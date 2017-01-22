package by.liudchyk.audiotracks.logic;

import by.liudchyk.audiotracks.dao.UserDAO;
import by.liudchyk.audiotracks.database.ConnectionPool;
import by.liudchyk.audiotracks.database.ProxyConnection;
import by.liudchyk.audiotracks.entity.User;
import by.liudchyk.audiotracks.exception.DAOException;
import by.liudchyk.audiotracks.exception.LogicException;
import by.liudchyk.audiotracks.validator.Validator;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.ArrayList;

/**
 * Class {@code UserLogic} is a service class used to connect commands
 * with UserDAO and does some logic connected with user.
 *
 * @author Liudchyk Valeriya
 * @see Validator
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

    /**
     * Validates all user's params. Transfers to userDAO to add new user
     *
     * @param name     is user's nickname
     * @param email    is user's email
     * @param password is user's password
     * @param confPass is user's confirm password
     * @param card     is user's card number
     * @return string message with success or error adding
     * @throws LogicException if validator throws LogicException
     */
    public String registerUser(String name, String email, String password, String confPass, String card) throws LogicException {
        Validator validator = new Validator();
        String msg = validator.isRegisterFormValid(name, password, confPass, card, email);
        if (!msg.isEmpty()) {
            return msg;
        }
        ProxyConnection connection = ConnectionPool.getInstance().takeConnection();
        UserDAO userDAO = new UserDAO(connection);
        String md5Pass = DigestUtils.md5Hex(password);
        try {
            return userDAO.addUser(name, email, md5Pass, card) ? MESSAGE : DEFAULT_MSG;
        } finally {
            userDAO.closeConnection(connection);
        }
    }

    /**
     * Validates new user's email. Transfers to userDAO to change user's email
     *
     * @param newEmail is new user's email
     * @param id       is user's id
     * @return string message with success or error changing
     * @throws LogicException if validator throws LogicException
     */
    public String changeUserEmail(String newEmail, int id) throws LogicException {
        Validator validator = new Validator();
        String msg = validator.isEmailChangeValid(newEmail);
        if (!msg.isEmpty()) {
            return msg;
        }
        ProxyConnection connection = ConnectionPool.getInstance().takeConnection();
        UserDAO userDAO = new UserDAO(connection);
        try {
            return userDAO.changeEmailById(newEmail, id) ? SUCCESS_MESSAGE : ERROR_MESSAGE;
        } finally {
            userDAO.closeConnection(connection);
        }
    }

    /**
     * Validates new user's nickname. Transfers to userDAO to change user's nickname
     *
     * @param newLogin is new user's nickname
     * @param id       is user's id
     * @return string message with success or error changing
     * @throws LogicException if validator throws LogicException
     */
    public String changeUserLogin(String newLogin, int id) throws LogicException {
        Validator validator = new Validator();
        String msg = validator.isLoginChangeValid(newLogin);
        if (!msg.isEmpty()) {
            return msg;
        }
        ProxyConnection connection = ConnectionPool.getInstance().takeConnection();
        UserDAO userDAO = new UserDAO(connection);
        try {
            return userDAO.changeLoginById(newLogin, id) ? SUCCESS_LOGIN_MESSAGE : ERROR_LOGIN_MESSAGE;
        } finally {
            userDAO.closeConnection(connection);
        }
    }

    /**
     * Validates new user's password. Transfers to userDAO to change user's password
     *
     * @param oldPassword     is old user's password
     * @param newPassword     is new user's password
     * @param newPasswordConf is confirmed new user's password
     * @param id              is user's id
     * @return string message with success or error changing
     * @throws LogicException if validator throws LogicException
     */
    public String changeUserPassword(String oldPassword, String newPassword, String newPasswordConf, int id) throws LogicException {
        Validator validator = new Validator();
        String msg = validator.isPasswordChangeValid(oldPassword, newPassword, newPasswordConf, id);
        if (!msg.isEmpty()) {
            return msg;
        }
        ProxyConnection connection = ConnectionPool.getInstance().takeConnection();
        UserDAO userDAO = new UserDAO(connection);
        String md5Password = DigestUtils.md5Hex(newPassword);
        try {
            return userDAO.changePasswordById(md5Password, id) ? SUCCESS_PASSWORD_MESSAGE : ERROR_PASSWORD_MESSAGE;
        } finally {
            userDAO.closeConnection(connection);
        }
    }

    /**
     * Validates new user's card number. Transfers to userDAO to change user's card number
     *
     * @param newCard is new user's card number
     * @param id      is user's id
     * @return string message with success or error changing
     * @throws LogicException if validator throws LogicException
     */
    public String changeUserCard(String newCard, int id) throws LogicException {
        Validator validator = new Validator();
        String msg = validator.isCardChangeValid(newCard);
        if (!msg.isEmpty()) {
            return msg;
        }
        ProxyConnection connection = ConnectionPool.getInstance().takeConnection();
        UserDAO userDAO = new UserDAO(connection);
        try {
            return userDAO.changeCardById(newCard, id) ? SUCCESS_CARD_MESSAGE : ERROR_CARD_MESSAGE;
        } finally {
            userDAO.closeConnection(connection);
        }
    }

    /**
     * Validates user's money. Transfers to userDAO to add money to account
     *
     * @param money is user's adding money
     * @param id    is user's id
     * @param card  is user's card number
     * @return string message with success or error changing
     */
    public String changeUserMoney(double money, int id, String card) {
        Validator validator = new Validator();
        String msg = validator.isMoneyChangeValid(money, card);
        if (!msg.isEmpty()) {
            return msg;
        }
        ProxyConnection connection = ConnectionPool.getInstance().takeConnection();
        UserDAO userDAO = new UserDAO(connection);
        try {
            return userDAO.changeMoneyById(money, id) ? SUCCESS_MONEY_MESSAGE : ERROR_MONEY_MESSAGE;
        } finally {
            userDAO.closeConnection(connection);
        }
    }

    /**
     * Transfer to userDAO to find user by nickname
     *
     * @param login is user's nickname
     * @return User with such id
     * @throws LogicException if userDAO throws DAOException
     */
    public User findUserByLogin(String login) throws LogicException {
        ProxyConnection connection = ConnectionPool.getInstance().takeConnection();
        UserDAO userDAO = new UserDAO(connection);
        try {
            return userDAO.findUser(login);
        } catch (DAOException e) {
            throw new LogicException("Can't find user by id", e);
        } finally {
            userDAO.closeConnection(connection);
        }
    }

    /**
     * Transfers to userDAO to find number of comments of user
     *
     * @param id is user's id
     * @return number of user's comments
     * @throws LogicException if userDAO throws DAOException
     */
    public int findNumberOfCommentsById(int id) throws LogicException {
        ProxyConnection connection = ConnectionPool.getInstance().takeConnection();
        UserDAO userDAO = new UserDAO(connection);
        try {
            return userDAO.commentsNumberById(id);
        } catch (DAOException e) {
            throw new LogicException("Can't find number of comments by id", e);
        } finally {
            userDAO.closeConnection(connection);
        }
    }

    /**
     * Validates user's bonus. Transfers to userDAO to change bonus
     *
     * @param nickname is user's nickname
     * @param bonus    is new user's bonus
     * @return new user's bonus,
     * -1 if error on validator
     * @throws LogicException if userDAO throws DAOException
     */
    public int changeBonusByNickname(String nickname, String bonus) throws LogicException {
        Validator validator = new Validator();
        if (!validator.isBonusValid(bonus)) {
            return -1;
        }
        ProxyConnection connection = ConnectionPool.getInstance().takeConnection();
        UserDAO userDAO = new UserDAO(connection);
        try {
            return userDAO.changeBonusByNickname(nickname, bonus);
        } catch (DAOException e) {
            throw new LogicException("Can't account bonus by nickname", e);
        } finally {
            userDAO.closeConnection(connection);
        }
    }

    /**
     * Transfers to userDAO to find user's bonus by user's id
     *
     * @param id is user's id
     * @return user's bonus
     * @throws LogicException if userDAO throws DAOException
     */
    public int findBonusById(int id) throws LogicException {
        ProxyConnection connection = ConnectionPool.getInstance().takeConnection();
        UserDAO userDAO = new UserDAO(connection);
        try {
            return userDAO.findBonusById(id);
        } catch (DAOException e) {
            throw new LogicException("Can't find bonus by id", e);
        } finally {
            userDAO.closeConnection(connection);
        }
    }

    /**
     * Transfers to userDAO to find user's money by user's id
     *
     * @param id is user's id
     * @return user's money
     * @throws LogicException if userDAO throws DAOException
     */
    public double findMoneyById(int id) throws LogicException {
        ProxyConnection connection = ConnectionPool.getInstance().takeConnection();
        UserDAO userDAO = new UserDAO(connection);
        try {
            return userDAO.findMoneyById(id);
        } catch (DAOException e) {
            throw new LogicException("Can't find money by id", e);
        } finally {
            userDAO.closeConnection(connection);
        }
    }

    /**
     * Transfered to userDAO to find all users
     *
     * @return ArrayList of all users
     * @throws LogicException if userDAO throws DAOException
     */
    public ArrayList<User> findAllUsers() throws LogicException {
        ProxyConnection connection = ConnectionPool.getInstance().takeConnection();
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
