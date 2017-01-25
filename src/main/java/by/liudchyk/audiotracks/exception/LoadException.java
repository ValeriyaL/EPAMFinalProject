package by.liudchyk.audiotracks.exception;

/**
 * Class {@code LoadException} is used to store exceptions in file loading.
 *
 * @author Liudchyk Valeriya
 * @version 1.0
 * @see Exception
 */
public class LoadException extends Exception {
    public LoadException() {
        super();
    }

    public LoadException(String message) {
        super(message);
    }

    public LoadException(String message, Throwable cause) {
        super(message, cause);
    }

    public LoadException(Throwable cause) {
        super(cause);
    }
}
