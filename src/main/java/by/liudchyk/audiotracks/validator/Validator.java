package by.liudchyk.audiotracks.validator;

import by.liudchyk.audiotracks.dao.TrackDAO;
import by.liudchyk.audiotracks.dao.UserDAO;
import by.liudchyk.audiotracks.database.ConnectionPool;
import by.liudchyk.audiotracks.database.ProxyConnection;
import by.liudchyk.audiotracks.exception.DAOException;
import by.liudchyk.audiotracks.exception.LogicException;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class {@code Validator} is used to validate all forms
 * and params from requests and check params uniqueness.
 *
 * @author Liudchyk Valeriya
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
    private final String EMPTY_STRING = "";
    private final int ZERO_LENGTH = 0;
    private final int MAX_TRACK_LENGTH = 1000;
    private final int MAX_GENRE_LENGTH = 45;
    private final int MIN_MONEY_LENGTH = 1;
    private final int MAX_MONEY_LENGTH = 4;
    private final int MAX_PRICE_LENGTH = 2;
    private final int MIN_LOGIN_LENGTH = 3;
    private final int MAX_LOGIN_LENGTH = 15;
    private final int MAX_TITLE_LENGTH = 200;
    private final int MIN_PASSWORD_LENGTH = 4;
    private final int MAX_PASSWORD_LENGTH = 20;
    private final int MIN_CARD_LENGTH = 13;
    private final int MAX_CARD_LENGTH = 18;
    private final int MAX_COMMENT_LENGTH = 65_535;
    private final int MAX_BONUS_LENGTH = 100;

    /**
     * Checks register form params
     *
     * @param name     is user's nickname
     * @param password is user's password
     * @param confirm  is user's confirm password
     * @param card     is user's card number
     * @param email    is user's email
     * @return empty string if params valid,
     * string with mistake if params not valid
     * @throws LogicException if loginUnique method throws LogicException
     */
    public String isRegisterFormValid(String name, String password, String confirm, String card, String email) throws LogicException {
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
        if (!card.isEmpty()) {
            if (!isCardUnique(card)) {
                return CARD_UNIQUE_MSG;
            }
        }
        return EMPTY_STRING;
    }

    /**
     * Check add track's form params
     *
     * @param title  is track's title
     * @param artist is track's artist
     * @param genre  is track's genre
     * @param price  is track's price
     * @param length is track's length
     * @return empty string if params valid,
     * string with mistake if params not valid
     * @throws LogicException if addGenreIfNotExists method throws LogicException
     */
    public String isAddTrackValid(String title, String artist, String genre, String price, String length) throws LogicException {
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
        if (!isGenreLengthValid(genre)) {
            return GENRE_LENGTH_MSG;
        }
        if (genre.length() > ZERO_LENGTH) {
            addGenreIfNotExists(genre);
        }
        return EMPTY_STRING;
    }

    /**
     * Checks is such genre in database and add it if not
     *
     * @param genre is track's genre
     * @return empty string if genre valid,
     * string with mistake if genre not valid
     * @throws LogicException if addGenreIfNotExists method throws LogicException
     */
    public String checkGenre(String genre) throws LogicException {
        if (!isGenreLengthValid(genre)) {
            return GENRE_LENGTH_MSG;
        }
        if (genre.length() > ZERO_LENGTH) {
            addGenreIfNotExists(genre);
        }
        return EMPTY_STRING;
    }

    /**
     * Transfer to trackDAO to add genre to database if it doesn't exist
     *
     * @param genre is track's genre
     * @throws LogicException trackDAO throws DAOException
     */
    private void addGenreIfNotExists(String genre) throws LogicException {
        ConnectionPool pool = ConnectionPool.getInstance();
        ProxyConnection connection = pool.takeConnection();
        TrackDAO trackDAO = new TrackDAO(connection);
        try {
            trackDAO.addGenreIfNotExists(genre);
        } catch (DAOException e) {
            throw new LogicException(e);
        } finally {
            trackDAO.closeConnection(connection);
        }
    }

    /**
     * Checks track's length
     *
     * @param length is track's length
     * @return true if length valid, false otherwise
     */
    public boolean isLengthValid(String length) {
        try {
            int len = Integer.valueOf(length);
            return len > ZERO_LENGTH && len < MAX_TRACK_LENGTH;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Checks track's genre length
     *
     * @param genre is track's genre
     * @return true if genre length valid, false otherwise
     */
    public boolean isGenreLengthValid(String genre) {
        return genre.length() < MAX_GENRE_LENGTH;
    }

    public String isEmailChangeValid(String newEmail) throws LogicException {
        if (!isEmailValid(newEmail)) {
            return EMAIL_MSG;
        }
        if (!isEmailUnique(newEmail)) {
            return EMAIL_UNIQUE_MSG;
        }
        return EMPTY_STRING;
    }

    /**
     * Checks is nickname changing valid
     *
     * @param newLogin is new user's nikname
     * @return empty string if nickname valid,
     * string with mistake otherwise
     * @throws LogicException if isLoginUnique method throws LogicException
     */
    public String isLoginChangeValid(String newLogin) throws LogicException {
        if (!isLoginLengthValid(newLogin)) {
            return LOGIN_MSG;
        }
        if (!isLoginUnique(newLogin)) {
            return LOGIN_UNIQUE_MSG;
        }
        return EMPTY_STRING;
    }

    /**
     * Checks is card number changing valid
     *
     * @param newCard is new user's card number
     * @return empty string if card number valid,
     * string with mistake otherwise
     * @throws LogicException if isCardUnique method throws LogicException
     */
    public String isCardChangeValid(String newCard) throws LogicException {
        if (!isCardValid(newCard)) {
            return CARD_MSG;
        }
        if (!newCard.isEmpty()) {
            if (!isCardUnique(newCard)) {
                return CARD_UNIQUE_MSG;
            }
        }
        return EMPTY_STRING;
    }

    /**
     * Checks is password change valid
     *
     * @param oldPass     is old user's password
     * @param newPass     is new user's password
     * @param newPassConf is new user's confirm password
     * @param id          is user's id
     * @return empty string if password valid,
     * string with mistake otherwise
     * @throws LogicException if isPasswordCorrect method throws LogicException
     */
    public String isPasswordChangeValid(String oldPass, String newPass, String newPassConf, int id) throws LogicException {
        if (!isPasswordCorrect(oldPass, id)) {
            return INCORRECT_PASSWORD_MSG;
        }
        if (!isPasswordLengthValid(newPass)) {
            return PASSWORD_MSG;
        }
        if (!isConfirmPasswordValid(newPass, newPassConf)) {
            return CONFIRM_MSG;
        }
        return EMPTY_STRING;
    }

    /**
     * Checks is money change valid
     *
     * @param money is user's money
     * @param card  is user's card number
     * @return empty string if money change valid,
     * string with mistake otherwise
     */
    public String isMoneyChangeValid(Double money, String card) {
        if (card == null || card.isEmpty()) {
            return CARD_IS_EMPTY_MSG;
        }
        if (String.valueOf(money).length() < MIN_MONEY_LENGTH && String.valueOf(money).length() > MAX_MONEY_LENGTH) {
            return INCORRECT_MONEY_MSG;
        }
        if (money < 0) {
            return INCORRECT_MONEY_MSG;
        }
        return EMPTY_STRING;
    }

    /**
     * Checks is track's price change valid
     *
     * @param price is track's price
     * @return empty string if price change valid,
     * string with mistake otherwise
     */
    public String isPriceChangeValid(String price) {
        try {
            Double newPrice = Double.valueOf(price);
            if (String.valueOf(newPrice.intValue()).length() > MAX_PRICE_LENGTH) {
                return INCORRECT_PRICE_LENGTH;
            }
            if (newPrice < 0) {
                return INCORRECT_PRICE_MSG;
            }
        } catch (NumberFormatException e) {
            return INCORRECT_PRICE_MSG;
        }
        return EMPTY_STRING;
    }

    /**
     * Checks is  nickname length valid
     *
     * @param login is user's nickname
     * @return true if nickname length is valid,
     * false otherwise
     */
    public boolean isLoginLengthValid(String login) {
        return login.length() > MIN_LOGIN_LENGTH && login.length() < MAX_LOGIN_LENGTH;
    }

    /**
     * Checks is  title length valid
     *
     * @param title is track's title
     * @return true if title length is valid,
     * false otherwise
     */
    public boolean isTitleLengthValid(String title) {
        return title.length() > ZERO_LENGTH && title.length() < MAX_TITLE_LENGTH;
    }

    /**
     * Checks is  password length valid
     *
     * @param password is user's password
     * @return true if password length is valid,
     * false otherwise
     */
    public boolean isPasswordLengthValid(String password) {
        return password.length() > MIN_PASSWORD_LENGTH && password.length() < MAX_PASSWORD_LENGTH;
    }

    /**
     * Checks is user's email valid
     *
     * @param email is user's email
     * @return true if email is valid,
     * false otherwise
     */
    public boolean isEmailValid(String email) {
        Pattern pattern = Pattern.compile(EMAIL_REGEXP);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    /**
     * Checks is confirm password equals password
     *
     * @param password is user's password
     * @param confPass is user's confirm password
     * @return true if equal, false otherwise
     */
    public boolean isConfirmPasswordValid(String password, String confPass) {
        return password.equals(confPass);
    }

    /**
     * Check is card number valid
     *
     * @param card is user's card number
     * @return true if card number valid, false otherwise
     */
    public boolean isCardValid(String card) {
        boolean res = false;
        if (card.isEmpty()) {
            return true;
        }
        if (card.length() >= MIN_CARD_LENGTH && card.length() <= MAX_CARD_LENGTH) {
            try {
                return Long.valueOf(card) > ZERO_LENGTH;
            } catch (NumberFormatException e) {
                LOG.debug("NumberFormatException in isCardValid", e);
            }
        }
        return res;
    }

    /**
     * Transfer to userDAO to check is user's nickname unique in system
     *
     * @param login is user's nickname
     * @return true if login is unique, false otherwise
     * @throws LogicException if userDAO throws DAOException
     */
    public boolean isLoginUnique(String login) throws LogicException {
        ConnectionPool pool = ConnectionPool.getInstance();
        ProxyConnection connection = pool.takeConnection();
        UserDAO userDAO = new UserDAO(connection);
        try {
            return userDAO.findUser(login) == null;
        } catch (DAOException e) {
            throw new LogicException(e);
        } finally {
            userDAO.closeConnection(connection);
        }
    }

    /**
     * Transfer to userDAO to check is user's email unique in system
     *
     * @param email is user's email
     * @return true if email is unique, false otherwise
     * @throws LogicException if userDAO throws DAOException
     */
    public boolean isEmailUnique(String email) throws LogicException {
        ConnectionPool pool = ConnectionPool.getInstance();
        ProxyConnection connection = pool.takeConnection();
        UserDAO userDAO = new UserDAO(connection);
        try {
            return userDAO.findUserByEmail(email) == null;
        } catch (DAOException e) {
            throw new LogicException(e);
        } finally {
            userDAO.closeConnection(connection);
        }
    }

    /**
     * Transfers to userDAO to check is password equals to password in DB
     *
     * @param password is user's password
     * @param id       is user's id
     * @return true if passwords are equals, false otherwise
     * @throws LogicException if userDAO throwsDAOException
     */
    public boolean isPasswordCorrect(String password, int id) throws LogicException {
        ConnectionPool pool = ConnectionPool.getInstance();
        ProxyConnection connection = pool.takeConnection();
        UserDAO userDAO = new UserDAO(connection);
        String md5Password = DigestUtils.md5Hex(password);
        try {
            return md5Password.equals(userDAO.findPasswordById(id));
        } catch (DAOException e) {
            throw new LogicException(e);
        } finally {
            userDAO.closeConnection(connection);
        }
    }

    /**
     * Transfer to userDAO to check is user's card number unique
     *
     * @param card is user's card number
     * @return true if card number unique, false otherwise
     * @throws LogicException if userDAO throws DAOException
     */
    public boolean isCardUnique(String card) throws LogicException {
        ConnectionPool pool = ConnectionPool.getInstance();
        ProxyConnection connection = pool.takeConnection();
        UserDAO userDAO = new UserDAO(connection);
        try {
            return userDAO.findUserByCard(card) == null;
        } catch (DAOException e) {
            throw new LogicException(e);
        } finally {
            userDAO.closeConnection(connection);
        }
    }

    /**
     * Checks is comment valid
     *
     * @param text is comment text
     * @return true if comment is valid, false otherwise
     */
    public String isCommentValid(String text) {
        return text.length() > ZERO_LENGTH && text.length() <= MAX_COMMENT_LENGTH ? EMPTY_STRING : COMMENT_LENGTH_MSG;
    }

    /**
     * Checks is user's bonus valid
     *
     * @param bonus is user's bonus
     * @return true if bonus valid, false otherwise
     */
    public boolean isBonusValid(String bonus) {
        int bon;
        try {
            bon = Integer.valueOf(bonus);
        } catch (NumberFormatException e) {
            return false;
        }
        return bon >= ZERO_LENGTH && bon <= MAX_BONUS_LENGTH;
    }
}
