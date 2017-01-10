package by.liudchyk.audiotracks.command;

import by.liudchyk.audiotracks.manager.ConfigurationManager;
import by.liudchyk.audiotracks.servlet.SessionRequestContent;

/**
 * Created by Admin on 23.12.2016.
 */
public class LanguageCommand extends ActionCommand {
    private static final String LANG_PARAMETER = "language";
    private static final String LOCALE_ATTRIBUTE = "locale";

    public String execute(SessionRequestContent requestContent) {
        String page;
        Object str = requestContent.getParameter(LANG_PARAMETER);
        if (str != null) {
            requestContent.setAttribute(LOCALE_ATTRIBUTE, str);
            requestContent.setSessionAttribute(LOCALE_ATTRIBUTE, str);
        }
        page = ConfigurationManager.getProperty((String) requestContent.getSessionAttribute(PATH_ATTRIBUTE));
        return page;
    }
}
