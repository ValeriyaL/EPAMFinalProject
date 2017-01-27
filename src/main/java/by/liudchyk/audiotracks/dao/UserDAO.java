package by.liudchyk.audiotracks.dao;

import by.liudchyk.audiotracks.database.ProxyConnection;
import by.liudchyk.audiotracks.entity.User;
import by.liudchyk.audiotracks.exception.DAOException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Class {@code UsertDAO} is used to connect with data base.
 * Does all actions connected with users.
 *
 * @author Liudchyk Valeriya
 * @see AbstractDAO
 */
@SuppressWarnings("Duplicates")
public class UserDAO extends AbstractDAO {
    private static final String SQL_FIND_PASSWORD_BY_LOGIN = "SELECT password FROM users WHERE nickname=?";
    private static final String SQL_FIND_PASSWORD_BY_ID = "SELECT password FROM users WHERE id=?";
    private static final String SQL_ADD_USER = "INSERT INTO users(nickname,password,card_number, email) VALUES(?,?,?,?)";
    private static final String SQL_ADD_USER_WITHOUT_CARD = "INSERT INTO users(nickname,password, email) VALUES(?,?,?)";
    private static final String SQL_FIND_USER_BY_LOGIN = "SELECT id, nickname, password, status, money, bonus, card_number, email FROM users WHERE nickname=?";
    private static final String SQL_FIND_USER_BY_EMAIL = "SELECT id, nickname, password, status, money, bonus, card_number, email FROM  users WHERE email=?";
    private static final String SQL_FIND_USER_BY_CARD = "SELECT id, nickname, password, status, money, bonus, card_number, email FROM  users WHERE card_number=?";
    private static final String SQL_CHANGE_LOGIN = "UPDATE users SET nickname=? WHERE id=?";
    private static final String SQL_CHANGE_EMAIL = "UPDATE users SET email=? WHERE id=?";
    private static final String SQL_CHANGE_CARD = "UPDATE users SET card_number=? WHERE id=?";
    private static final String SQL_CHANGE_PASSWORD = "UPDATE users SET password=? WHERE id=?";
    private static final String SQL_CHANGE_MONEY = "UPDATE users SET money=? WHERE id=?";
    private static final String SQL_COMMENTS_NUMBER_BY_ID = "SELECT count(*) FROM comments WHERE user_id=?";
    private static final String SQL_SET_BONUS_BY_NICKNAME = "UPDATE users SET bonus=? WHERE nickname=?";
    private static final String SQL_FIND_BONUS = "SELECT bonus FROM users WHERE nickname=?";
    private static final String SQL_FIND_BONUS_BY_ID = "SELECT bonus FROM users WHERE id=?";
    private static final String SQL_FIND_MONEY_BY_ID = "SELECT money FROM users WHERE id=?";
    private static final String SQL_SELECT_ALL_USERS = "SELECT id, nickname, password, status, money, bonus, card_number, email FROM users ORDER BY nickname";

    public UserDAO(ProxyConnection connection) {
        super(connection);
    }

    /**
     * Finds user's password by id
     *
     * @param id is user's id
     * @return user's password from database
     * @throws DAOException if SQLException was thrown
     */
    public String findPasswordById(int id) throws DAOException {
        String password = null;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_FIND_PASSWORD_BY_ID);
            statement.setString(1, Integer.toString(id));
            ResultSet set = statement.executeQuery();
            if (set.next()) {
                password = set.getString(1);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            closeStatement(statement);
        }
        return password;
    }

    /**
     * Finds user's password by login
     *
     * @param login is user's login
     * @return user's password from database
     * @throws DAOException if SQLException was thrown
     */
    public String findPasswordForLogin(String login) throws DAOException {
        String password = null;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_FIND_PASSWORD_BY_LOGIN);
            statement.setString(1, login);
            ResultSet set = statement.executeQuery();
            if (set.next()) {
                password = set.getString(1);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            closeStatement(statement);
        }
        return password;
    }

    /**
     * Adds new user to database
     *
     * @param name     is user's name
     * @param email    is user's email
     * @param password is user's password
     * @param card     is user's card
     * @return true if user was added, false otherwise
     */
    public boolean addUser(String name, String email, String password, String card) {
        boolean isAdded = false;
        PreparedStatement statement = null;
        try {
            if (!card.isEmpty()) {
                statement = connection.prepareStatement(SQL_ADD_USER);
                statement.setString(1, name);
                statement.setString(2, password);
                statement.setString(3, card);
                statement.setString(4, email);
            } else {
                statement = connection.prepareStatement(SQL_ADD_USER_WITHOUT_CARD);
                statement.setString(1, name);
                statement.setString(2, password);
                statement.setString(3, email);
            }
            statement.executeUpdate();
            isAdded = true;
        } catch (SQLException e) {
            LOG.warn("SQLException in adding user", e);
        } finally {
            closeStatement(statement);
        }
        return isAdded;
    }

    /**
     * Finds user by user's nickname
     *
     * @param login is user's nickname
     * @return User object
     * @throws DAOException if SQLException was thrown
     */
    public User findUser(String login) throws DAOException {
        User tempUser = null;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_FIND_USER_BY_LOGIN);
            statement.setString(1, login);
            tempUser = takeUser(tempUser, statement);
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            closeStatement(statement);
        }
        return tempUser;
    }

    /**
     * Finds user by email
     *
     * @param email is user's email
     * @return User object
     * @throws DAOException if SQLException was thrown
     */
    public User findUserByEmail(String email) throws DAOException {
        User tempUser = null;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_FIND_USER_BY_EMAIL);
            statement.setString(1, email);
            tempUser = takeUser(tempUser, statement);
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            closeStatement(statement);
        }
        return tempUser;
    }

    /**
     * Finds user by card number
     *
     * @param card is user's card number
     * @return User object
     * @throws DAOException if SQLException was thrown
     */
    public User findUserByCard(String card) throws DAOException {
        User tempUser = null;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_FIND_USER_BY_CARD);
            statement.setString(1, card);
            tempUser = takeUser(tempUser, statement);
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            closeStatement(statement);
        }
        return tempUser;
    }

    /**
     * Takes user from database
     *
     * @param tempUser  is User object
     * @param statement is Statement with query
     * @return User object
     * @throws DAOException if SQLException was thrown
     */
    private User takeUser(User tempUser, PreparedStatement statement) throws DAOException {
        try {
            ResultSet set = statement.executeQuery();
            if (set.next()) {
                int id = set.getInt(1);
                String name = set.getString(2);
                String password = set.getString(3);
                int status = set.getInt(4);
                double money = set.getDouble(5);
                int bonus = set.getInt(6);
                String card = set.getString(7);
                String mail = set.getString(8);
                tempUser = new User(id, name, password, status, money, bonus, card, mail);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return tempUser;
    }

    /**
     * Changes user's nickname by id
     *
     * @param newLogin is new user's nickname
     * @param id       is user's id
     * @return true if nickname was changed, false otherwise
     */
    public boolean changeLoginById(String newLogin, int id) {
        boolean isAdded = false;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_CHANGE_LOGIN);
            statement.setString(1, newLogin);
            statement.setString(2, Integer.toString(id));
            int i = statement.executeUpdate();
            if (i != 0) {
                isAdded = true;
            }
        } catch (SQLException e) {
            LOG.warn("SQLException in changeLoginById", e);
        } finally {
            closeStatement(statement);
        }
        return isAdded;
    }

    /**
     * Changes user's password by id
     *
     * @param newMD5Password is new user's password in md5
     * @param id             is user's id
     * @return true if password was changed, false otherwise
     */
    public boolean changePasswordById(String newMD5Password, int id) {
        boolean isAdded = false;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_CHANGE_PASSWORD);
            statement.setString(1, newMD5Password);
            statement.setString(2, Integer.toString(id));
            int i = statement.executeUpdate();
            if (i != 0) {
                isAdded = true;
            }
        } catch (SQLException e) {
            LOG.warn("SQLException in changePasswordById", e);
        } finally {
            closeStatement(statement);
        }
        return isAdded;
    }

    /**
     * Changes user's money by id
     *
     * @param money is user's money
     * @param id    is user's id
     * @return true if user's money was changed, false otherwise
     */
    public boolean changeMoneyById(double money, int id) {
        boolean isAdded = false;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_CHANGE_MONEY);
            statement.setDouble(1, money);
            statement.setString(2, Integer.toString(id));
            int i = statement.executeUpdate();
            if (i != 0) {
                isAdded = true;
            }
        } catch (SQLException e) {
            LOG.warn("SQLException in changeMoneyMyId", e);
        } finally {
            closeStatement(statement);
        }
        return isAdded;
    }

    /**
     * Changes user's card number by id
     *
     * @param newCard is new user's card number
     * @param id      is user's id
     * @return true if user's card nember was changed, false otherwise
     */
    public boolean changeCardById(String newCard, int id) {
        boolean isAdded = false;
        PreparedStatement statement = null;
        try {
            if (!newCard.isEmpty()) {
                statement = connection.prepareStatement(SQL_CHANGE_CARD);
                statement.setString(1, newCard);
                statement.setString(2, Integer.toString(id));
            } else {
                statement = connection.prepareStatement(SQL_CHANGE_CARD);
                statement.setString(1, null);
                statement.setString(2, Integer.toString(id));
            }
            int i = statement.executeUpdate();
            if (i != 0) {
                isAdded = true;
            }
        } catch (SQLException e) {
            LOG.warn("SQLException in changeCardById", e);
        } finally {
            closeStatement(statement);
        }
        return isAdded;
    }

    /**
     * Changes user's email by id
     *
     * @param newEmail is new user's email
     * @param id       is user's id
     * @return true if email was changed, false otherwise
     */
    public boolean changeEmailById(String newEmail, int id) {
        boolean isAdded = false;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_CHANGE_EMAIL);
            statement.setString(1, newEmail);
            statement.setString(2, Integer.toString(id));
            int i = statement.executeUpdate();
            if (i != 0) {
                isAdded = true;
            }
        } catch (SQLException e) {
            LOG.warn("SQLException in changeEmailById", e);
        } finally {
            closeStatement(statement);
        }
        return isAdded;
    }

    /**
     * Counts number of all user's comments
     *
     * @param id is user's id
     * @return number of user's comments
     * @throws DAOException if SQLException was thrown
     */
    public int commentsNumberById(int id) throws DAOException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_COMMENTS_NUMBER_BY_ID);
            statement.setInt(1, id);
            ResultSet set = statement.executeQuery();
            if (set.next()) {
                return set.getInt(1);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            closeStatement(statement);
        }
        return 0;
    }

    /**
     * Changes user's bonus by nickname
     *
     * @param nickname is user's nickname
     * @param bonus    is new user's bonus
     * @return new user's bonus or -1 if something was wrong
     * @throws DAOException if SQLException was thrown
     */
    public int changeBonusByNickname(String nickname, String bonus) throws DAOException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_SET_BONUS_BY_NICKNAME);
            statement.setString(1, bonus);
            statement.setString(2, nickname);
            statement.executeUpdate();
            statement = connection.prepareStatement(SQL_FIND_BONUS);
            statement.setString(1, nickname);
            ResultSet set = statement.executeQuery();
            if (set.next()) {
                return set.getInt(1);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            closeStatement(statement);
        }
        return -1;
    }

    /**
     * Finds user's bonus by id
     *
     * @param id is user's id
     * @return user's bonus
     * @throws DAOException if SQLException was thrown
     */
    public int findBonusById(int id) throws DAOException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_FIND_BONUS_BY_ID);
            statement.setInt(1, id);
            ResultSet set = statement.executeQuery();
            set.next();
            return set.getInt(1);
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            closeStatement(statement);
        }
    }

    /**
     * Finds user's money by id
     *
     * @param id is user's id
     * @return user's money
     * @throws DAOException if SQLException was thrown
     */
    public double findMoneyById(int id) throws DAOException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_FIND_MONEY_BY_ID);
            statement.setInt(1, id);
            ResultSet set = statement.executeQuery();
            set.next();
            return set.getDouble(1);
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            closeStatement(statement);
        }
    }

    /**
     * Finds all users
     *
     * @return list of Users
     * @throws DAOException if SQLException was thrown
     */
    public ArrayList<User> findAllUsers() throws DAOException {
        ArrayList<User> users = new ArrayList<>();
        Statement statement = null;
        try {
            statement = connection.createStatement();
            ResultSet set = statement.executeQuery(SQL_SELECT_ALL_USERS);
            User tempUser;
            while (set.next()) {
                int id = set.getInt(1);
                String name = set.getString(2);
                String password = set.getString(3);
                int status = set.getInt(4);
                double money = set.getDouble(5);
                int bonus = set.getInt(6);
                String card = set.getString(7);
                String mail = set.getString(8);
                tempUser = new User(id, name, password, status, money, bonus, card, mail);
                users.add(tempUser);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            closeStatement(statement);
        }
        return users;
    }
}
