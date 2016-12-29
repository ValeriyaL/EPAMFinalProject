package by.liudchyk.audiotracks.command;

import by.liudchyk.audiotracks.servlet.SessionRequestContent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class ActionCommand {
    static final Logger LOG = LogManager.getLogger();
    public static final String ERROR_MSG_ATTRIBUTE = "errorMessage";
    public static final String USER_ATTRIBUTE = "user";
    public static final String SUCCESS_ATTRIBUTE = "info";
    public static final String MISTAKE_ATTRIBUTE = "mistake";
    public static final String PATH_ATTRIBUTE = "page";
    public static final String ERROR_PATH = "path.page.error";
    public static final String PARAMETER = "locale";

    public abstract String execute(SessionRequestContent requestContent);
}
