package by.liudchyk.audiotracks.command;

import by.liudchyk.audiotracks.manager.ConfigurationManager;
import by.liudchyk.audiotracks.servlet.SessionRequestContent;

/**
 * Created by Admin on 23.12.2016.
 */
public class LanguageCommand implements ActionCommand {
    private static final String PARAMETER = "language";
    private static final String LOCALE_ATTRIBUTE = "locale";
    private static final String PATH_ATTRIBUTE = "page";

    public String execute(SessionRequestContent requestContent) {
        String page = null;
        Object str = requestContent.getParameter(PARAMETER);
        if (str != null) {
            requestContent.setAttribute(LOCALE_ATTRIBUTE, str);
            requestContent.setSessionAttribute(LOCALE_ATTRIBUTE, str);
        }
        page = ConfigurationManager.getProperty((String) requestContent.getSessionAttribute(PATH_ATTRIBUTE));
        return page;
    }
}
