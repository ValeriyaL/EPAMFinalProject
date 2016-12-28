package by.liudchyk.audiotracks.dao;

import by.liudchyk.audiotracks.database.ProxyConnection;
import by.liudchyk.audiotracks.entity.User;
import by.liudchyk.audiotracks.exception.DAOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 24.12.2016.
 */
public class UserDAO extends AbstractDAO {
    private static final Logger LOG = LogManager.getLogger();
    private static final String SQL_SELECT_ALL_USERS = "SELECT * FROM users";
    private static final String SQL_FIND_PASSWORD = "SELECT password FROM users WHERE nickname=?";
    private static final String SQL_FIND_PASSWORD_BY_ID = "SELECT password FROM users WHERE id=?";
    private static final String SQL_ADD_USER = "INSERT INTO users(nickname,password,card_number, email) VALUES(?,?,?,?)";
    private static final String SQL_ADD_USER_WITHOUT_CARD = "INSERT INTO users(nickname,password, email) VALUES(?,?,?)";
    private static final String SQL_FIND_USER_BY_LOGIN = "SELECT * FROM users WHERE nickname=?";
    private static final String SQL_FIND_USER_BY_EMAIL = "SELECT * FROM  users WHERE email=?";
    private static final String SQL_FIND_USER_BY_CARD = "SELECT * FROM  users WHERE card_number=?";
    private static final String SQL_CHANGE_LOGIN = "UPDATE users SET nickname=? WHERE id=?";
    private static final String SQL_CHANGE_EMAIL = "UPDATE users SET email=? WHERE id=?";
    private static final String SQL_CHANGE_CARD = "UPDATE users SET card_number=? WHERE id=?";
    private static final String SQL_CHANGE_PASSWORD = "UPDATE users SET password=? WHERE id=?";
    private static final String SQL_CHANGE_MONEY = "UPDATE users SET money=? WHERE id=?";

    public UserDAO(ProxyConnection connection) {
        super(connection);
    }

    @Override
    public List findAll() throws DAOException {
        ArrayList<User> users = new ArrayList<>();
        Statement statement = null;
        try {
            statement = connection.createStatement();
            ResultSet set = statement.executeQuery(SQL_SELECT_ALL_USERS);
            users = new ArrayList<>();
            while (set.next()) {
                int id = set.getInt(1);
                String nick = set.getString(2);
                String password = set.getString(3);
                int status = set.getInt(4);
                double money = set.getDouble(5);
                int bonus = set.getInt(6);
                String card = set.getString(7);
                String email = set.getString(8);
                users.add(new User(id, nick, password, status, money, bonus, card,email));
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            closeStatement(statement);
        }
        return users;
    }

    public List findColumnNames() throws DAOException {
        ArrayList<String> header = new ArrayList<>();
        Statement statement = null;
        try {
            statement = connection.createStatement();
            ResultSet set = statement.executeQuery(SQL_SELECT_ALL_USERS);
            ResultSetMetaData metaData = set.getMetaData();
            int col = metaData.getColumnCount();
            for (int i = 1; i <= col; i++) {
                header.add(metaData.getColumnName(i));
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }finally {
            closeStatement(statement);
        }
        return header;
    }

    public String findPasswordById(int id) throws DAOException{
        String password = null;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_FIND_PASSWORD_BY_ID);
            statement.setString(1,""+id);
            ResultSet set = statement.executeQuery();
            if(set.next()){
                password = set.getString(1);
            }
        }catch (SQLException e){
            throw new DAOException(e);
        }finally {
            closeStatement(statement);
        }
        return password;
    }
    public String findPasswordForLogin(String login) throws DAOException{
        String password = null;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_FIND_PASSWORD);
            statement.setString(1,login);
            ResultSet set = statement.executeQuery();
            if(set.next()){
                password = set.getString(1);
            }
        }catch (SQLException e){
            throw new DAOException(e);
        }finally {
            closeStatement(statement);
        }
        return password;
    }

    public boolean addUser(String name, String email, String password, String card) throws DAOException{
        boolean isAdded;
        PreparedStatement statement = null;
        try {
            if(!card.isEmpty()) {
                statement = connection.prepareStatement(SQL_ADD_USER);
                statement.setString(1, name);
                statement.setString(2, password);
                statement.setString(3, card);
                statement.setString(4, email);
            }else{
                statement = connection.prepareStatement(SQL_ADD_USER_WITHOUT_CARD);
                statement.setString(1, name);
                statement.setString(2, password);
                statement.setString(3, email);
            }
            statement.executeUpdate();
            isAdded = true;
        }catch (SQLException e){
            //throw new DAOException(e);
            isAdded = false;
        }finally {
            closeStatement(statement);
        }
        return isAdded;
    }

    public User findUser(String login) throws DAOException{
        User tempUser = null;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_FIND_USER_BY_LOGIN);
            statement.setString(1,login);
            tempUser = takeUser(tempUser, statement);
        }catch (SQLException e){
            throw new DAOException(e);
        }finally {
            closeStatement(statement);
        }
        return tempUser;
    }

    public User findUserByEmail(String email) throws DAOException{
        User tempUser = null;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_FIND_USER_BY_EMAIL);
            statement.setString(1,email);
            tempUser = takeUser(tempUser, statement);
        }catch (SQLException e){
            throw new DAOException(e);
        }finally {
            closeStatement(statement);
        }
        return tempUser;
    }

    public User findUserByCard(String card) throws DAOException{
        User tempUser = null;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_FIND_USER_BY_CARD);
            statement.setString(1,card);
            tempUser = takeUser(tempUser, statement);
        }catch (SQLException e){
            throw new DAOException(e);
        }finally {
            closeStatement(statement);
        }
        return tempUser;
    }

    private User takeUser(User tempUser, PreparedStatement statement) throws SQLException {
        ResultSet set = statement.executeQuery();
        if(set.next()){
            int id = set.getInt(1);
            String name = set.getString(2);
            String password = set.getString(3);
            int status = set.getInt(4);
            double money = set.getDouble(5);
            int bonus = set.getInt(6);
            String card = set.getString(7);
            String mail = set.getString(8);
            tempUser = new User(id,name,password,status,money,bonus,card,mail);
        }
        return tempUser;
    }

    public boolean changeLoginById(String newLogin, int id) throws DAOException{
        boolean isAdded;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_CHANGE_LOGIN);
            statement.setString(1, newLogin);
            statement.setString(2, "" + id);
            int i = statement.executeUpdate();
            if(i!=0){
                isAdded = true;
            }else{
                isAdded = false;
            }
        }catch (SQLException e){
           // throw new DAOException(e);
            isAdded = false;
        }finally {
            closeStatement(statement);
        }
        return isAdded;
    }

    public boolean changePasswordById(String newMD5Password, int id) throws DAOException{
        boolean isAdded;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_CHANGE_PASSWORD);
            statement.setString(1, newMD5Password);
            statement.setString(2, "" + id);
            int i = statement.executeUpdate();
            if(i!=0){
                isAdded = true;
            }else{
                isAdded = false;
            }
        }catch (SQLException e){
            // throw new DAOException(e);
            isAdded = false;
        }finally {
            closeStatement(statement);
        }
        return isAdded;
    }

    public boolean changeMoneyById(double money, int id) throws DAOException{
        boolean isAdded;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_CHANGE_MONEY);
            statement.setDouble(1, money);
            statement.setString(2, "" + id);
            int i = statement.executeUpdate();
            if(i!=0){
                isAdded = true;
            }else{
                isAdded = false;
            }
        }catch (SQLException e){
            // throw new DAOException(e);
            isAdded = false;
        }finally {
            closeStatement(statement);
        }
        return isAdded;
    }

    public boolean changeCardById(String newCard, int id) throws DAOException{
        boolean isAdded;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_CHANGE_CARD);
            statement.setString(1, newCard);
            statement.setString(2, "" + id);
            int i = statement.executeUpdate();
            if(i!=0){
                isAdded = true;
            }else{
                isAdded = false;
            }
        }catch (SQLException e){
            // throw new DAOException(e);
            isAdded = false;
        }finally {
            closeStatement(statement);
        }
        return isAdded;
    }

    public boolean changeEmailById(String newEmail, int id) throws DAOException{
        boolean isAdded;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_CHANGE_EMAIL);
            statement.setString(1, newEmail);
            statement.setString(2, "" + id);
            int i = statement.executeUpdate();
            if(i!=0){
                isAdded = true;
            }else {
                isAdded = false;
            }
        }catch (SQLException e){
            isAdded = false;
           // throw new DAOException(e);
        }finally {
            closeStatement(statement);
        }
        return isAdded;
    }
}
