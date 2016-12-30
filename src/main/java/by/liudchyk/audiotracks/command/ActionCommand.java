package by.liudchyk.audiotracks.command;

import by.liudchyk.audiotracks.manager.ConfigurationManager;
import by.liudchyk.audiotracks.servlet.SessionRequestContent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class ActionCommand {
    static final Logger LOG = LogManager.getLogger();
    static final String ERROR_MSG_ATTRIBUTE = "errorMessage";
    static final String USER_ATTRIBUTE = "user";
    static final String SUCCESS_ATTRIBUTE = "info";
    static final String MISTAKE_ATTRIBUTE = "mistake";
    static final String PATH_ATTRIBUTE = "page";
    static final String ERROR_PATH = "path.page.error";
    static final String PARAMETER = "locale";

    public abstract String execute(SessionRequestContent requestContent);

  /*  public String redirectToErrorPage(SessionRequestContent requestContent, Exception e){
        LOG.error(e);
        requestContent.setAttribute(ERROR_MSG_ATTRIBUTE, e.getMessage());
        return ConfigurationManager.getProperty(ERROR_PATH);
    }*/
}
