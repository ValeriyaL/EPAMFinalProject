package by.liudchyk.audiotracks.command;

import by.liudchyk.audiotracks.manager.ConfigurationManager;
import by.liudchyk.audiotracks.servlet.SessionRequestContent;

/**
 * Class {@code LanguageCommand} is used to change language in session
 * and redirect to the same page
 *
 * @author LiudchykValeriya
 * @see ActionCommand
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
