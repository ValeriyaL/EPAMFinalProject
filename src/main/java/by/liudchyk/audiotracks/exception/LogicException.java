package by.liudchyk.audiotracks.exception;

/**
 * Class {@code LogicException} is used to store Logic level exceptions.
 *
 * @author Liudchyk Valeriya
 * @version 1.0
 * @see Exception
 */
public class LogicException extends Exception {
    public LogicException() {
        super();
    }

    public LogicException(String message) {
        super(message);
    }

    public LogicException(String message, Throwable cause) {
        super(message, cause);
    }

    public LogicException(Throwable cause) {
        super(cause);
    }
}
