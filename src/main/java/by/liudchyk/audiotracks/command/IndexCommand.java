package by.liudchyk.audiotracks.command;

import by.liudchyk.audiotracks.servlet.SessionRequestContent;

/**
 * Created by Admin on 28.12.2016.
 */
public class IndexCommand extends ActionCommand {
    private static final String LOCALE_ATTRIBUTE = "locale";
    private static final String DEFAULT_LOCALE = "en_US";

    @Override
    public String execute(SessionRequestContent requestContent) {
        requestContent.setAttribute(LOCALE_ATTRIBUTE, DEFAULT_LOCALE);
        requestContent.setSessionAttribute(LOCALE_ATTRIBUTE, DEFAULT_LOCALE);
        return redirectToMain(requestContent);
    }
}
