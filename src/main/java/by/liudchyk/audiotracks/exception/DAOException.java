package by.liudchyk.audiotracks.exception;

import by.liudchyk.audiotracks.entity.Entity;

/**
 * Class {@code DAOException} is used to store DAO level exceptions.
 *
 * @author Liudchyk Valeriya
 * @version 1.0
 * @see Exception
 */
public class DAOException extends Exception {
    public DAOException() {
        super();
    }

    public DAOException(String message) {
        super(message);
    }

    public DAOException(String message, Throwable cause) {
        super(message, cause);
    }

    public DAOException(Throwable cause) {
        super(cause);
    }
}
