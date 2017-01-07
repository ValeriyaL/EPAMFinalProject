package by.liudchyk.audiotracks.validator;

import by.liudchyk.audiotracks.dao.TrackDAO;
import by.liudchyk.audiotracks.dao.UserDAO;
import by.liudchyk.audiotracks.database.ConnectionPool;
import by.liudchyk.audiotracks.database.ProxyConnection;
import by.liudchyk.audiotracks.entity.Track;
import by.liudchyk.audiotracks.entity.User;
import by.liudchyk.audiotracks.exception.DAOException;
import by.liudchyk.audiotracks.exception.LogicException;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Admin on 24.12.2016.
 */
public class Validator {
    private static final Logger LOG = LogManager.getLogger();
    private final String MUSIC_PATH = "D:\\music\\";
    private final String EMAIL_REGEXP = ".+@[a-z]{2,6}\\.[a-z]{2,4}";
    private final String LOGIN_MSG = "message.lengthLogin";
    private final String PASSWORD_MSG = "message.lengthPass";
    private final String EMAIL_MSG = "message.email";
    private final String CARD_MSG = "message.lengthCard";
    private final String CONFIRM_MSG = "message.confirm";
    private final String LOGIN_UNIQUE_MSG = "message.login.unique";
    private final String EMAIL_UNIQUE_MSG = "message.email.unique";
    private final String CARD_UNIQUE_MSG = "message.card.unique";
    private final String INCORRECT_PASSWORD_MSG = "message.error.password.incorrect";
    private final String INCORRECT_MONEY_MSG = "message.error.money.incorrect";
    private final String CARD_IS_EMPTY_MSG = "message.error.card.empty";
    private final String COMMENT_LENGTH_MSG = "message.error.comment.length";
    private final String INCORRECT_PRICE_LENGTH = "message.error.price.length";
    private final String INCORRECT_PRICE_MSG = "message.error.price";
    private final String TITLE_MSG = "message.error.title";
    private final String ARTIST_MSG = "message.error.artist";
    private final String LENGTH_MSG = "message.error.length";
    private final String GENRE_LENGTH_MSG = "message.error.genre.length";
    private final String LINK_LENGTH_MSG = "message.error.link.length";
    private final String LINK_MSG = "message.error.link.exist";

    public String isRegisterFormValid(String name, String password, String confirm, String card, String email) throws LogicException {
        String res = "";
        if (!isLoginLengthValid(name)) {
            return LOGIN_MSG;
        }
        if (!isPasswordLengthValid(password)) {
            return PASSWORD_MSG;
        }
        if (!isEmailValid(email)) {
            return EMAIL_MSG;
        }
        if (!isCardValid(card)) {
            return CARD_MSG;
        }
        if (!isConfirmPasswordValid(password, confirm)) {
            return CONFIRM_MSG;
        }
        if (!isLoginUnique(name)) {
            return LOGIN_UNIQUE_MSG;
        }
        if (!isEmailUnique(email)) {
            return EMAIL_UNIQUE_MSG;
        }
        if(!card.isEmpty()) {
            if (!isCardUnique(card)) {
                return CARD_UNIQUE_MSG;
            }
        }
        return res;
    }

    public String isAddTrackValid(String title,String artist,String genre,String price,String link,String length) throws LogicException{
        String res = "";
        if (!isTitleLengthValid(title)) {
            return TITLE_MSG;
        }
        if (!isTitleLengthValid(artist)) {
            return ARTIST_MSG;
        }
        if (!isPriceChangeValid(price).isEmpty()) {
            return isPriceChangeValid(price);
        }
        if (!isLengthValid(length)) {
            return LENGTH_MSG;
        }
        if (!isGenreLengthValid(genre)){
            return GENRE_LENGTH_MSG;
        }
        if (genre.length()>0){
            ConnectionPool pool = ConnectionPool.getInstance();
            ProxyConnection connection = pool.getConnection();
            TrackDAO trackDAO = new TrackDAO(connection);
            try {
                trackDAO.addGenreIfNotExists(genre);
            } catch (DAOException e) {
                throw new LogicException(e);
            } finally {
                trackDAO.closeConnection(connection);
            }
        }
        if(!isLinkLengthValid(link)){
            return LINK_LENGTH_MSG;
        }
        if(!isLinkValid(link)){
            return LINK_MSG;
        }
        return res;
    }

    public boolean isLinkValid(String link){
        File file = new File(MUSIC_PATH+link);
        return file.exists() && file.isFile();
    }

    public boolean isLinkLengthValid(String link){
        return link.length()>0 && link.length()<300;
    }

    public boolean isLengthValid(String length){
        try{
            int len = Integer.valueOf(length);
            return  len > 0 && len <1000;
        }catch (NumberFormatException e){
            return false;
        }
    }

    public boolean isGenreLengthValid(String genre){
        return genre.length()<45;
    }

    public String isEmailChangeValid(String newEmail) throws LogicException {
        String res = "";
        if (!isEmailValid(newEmail)) {
            return EMAIL_MSG;
        }
        if (!isEmailUnique(newEmail)) {
            return EMAIL_UNIQUE_MSG;
        }
        return res;
    }

    public String isLoginChangeValid(String newLogin) throws LogicException {
        String res = "";
        if (!isLoginLengthValid(newLogin)) {
            return LOGIN_MSG;
        }
        if (!isLoginUnique(newLogin)) {
            return LOGIN_UNIQUE_MSG;
        }
        return res;
    }

    public String isCardChangeValid(String newCard) throws LogicException {
        String res = "";
        if (!isCardValid(newCard)) {
            return CARD_MSG;
        }
        if(!newCard.isEmpty()) {
            if (!isCardUnique(newCard)) {
                return CARD_UNIQUE_MSG;
            }
        }
        return res;
    }

    public String isPasswordChangeValid(String oldPass, String newPass, String newPassConf, int id) throws LogicException {
        String res = "";
        if (!isPasswordCorrect(oldPass, id)) {
            return INCORRECT_PASSWORD_MSG;
        }
        if (!isPasswordLengthValid(newPass)) {
            return PASSWORD_MSG;
        }
        if (!isConfirmPasswordValid(newPass, newPassConf)) {
            return CONFIRM_MSG;
        }
        return res;
    }

    public String isMoneyChangeValid(Double money, String card) {
        if(card == null || card.isEmpty()){
            return CARD_IS_EMPTY_MSG;
        }
        String res = "";
        if (String.valueOf(money).length() < 1 && String.valueOf(money).length() > 4) {
            return INCORRECT_MONEY_MSG;
        }
        return res;
    }

    public String isPriceChangeValid(String price){
        try {
            Double newPrice = Double.valueOf(price);
            if(String.valueOf(newPrice.intValue()).length() > 2){
                return INCORRECT_PRICE_LENGTH;
            }
        }catch (NumberFormatException e){
            return INCORRECT_PRICE_MSG;
        }
        return "";
    }

    public boolean isLoginLengthValid(String login) {
        return login.length() > 3 && login.length() < 15;
    }

    public boolean isTitleLengthValid(String title) {
        return title.length() > 0 && title.length() < 200;
    }

    public boolean isPasswordLengthValid(String password) {
        return password.length() > 4 && password.length() < 20;
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
        if(card.isEmpty()){
            return true;
        }
        if (card.length() >= 13 && card.length() <= 18) {
            try{
                Long.valueOf(card);
            }catch (NumberFormatException e){
                return false;
            }
            return true;
        } else {
            return false;
        }
    }

    public boolean isLoginUnique(String login) throws LogicException {
        ConnectionPool pool = ConnectionPool.getInstance();
        ProxyConnection connection = pool.getConnection();
        UserDAO userDAO = new UserDAO(connection);
        try {
            return userDAO.findUser(login) == null;
        } catch (DAOException e) {
            throw new LogicException(e);
        } finally {
            userDAO.closeConnection(connection);
        }
    }

    public boolean isEmailUnique(String email) throws LogicException {
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
            userDAO.closeConnection(connection);
        }
    }

    public boolean isPasswordCorrect(String password, int id) throws LogicException {
        ConnectionPool pool = ConnectionPool.getInstance();
        ProxyConnection connection = pool.getConnection();
        UserDAO userDAO = new UserDAO(connection);
        String md5Password = DigestUtils.md5Hex(password);
        try {
            if (md5Password.equals(userDAO.findPasswordById(id))) {
                return true;
            } else {
                return false;
            }
        } catch (DAOException e) {
            throw new LogicException(e);
        } finally {
            userDAO.closeConnection(connection);
        }
    }

    public boolean isCardUnique(String card) throws LogicException {
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
            userDAO.closeConnection(connection);
        }
    }

    public String isCommentValid(String text){
        if(text.length()>0 && text.length()<= 65535){
            return "";
        }else{
            return COMMENT_LENGTH_MSG;
        }
    }

    public boolean isBonusValid(String bonus){
        int bon;
        try{
            bon = Integer.valueOf(bonus);
        }catch (NumberFormatException e){
            return false;
        }
        return bon>=0 && bon<=100;
    }
}
