package by.liudchyk.audiotracks.command;

import by.liudchyk.audiotracks.servlet.SessionRequestContent;

/**
 * Class {@code IndexCommand} is used to redirect
 * from index page to mane after filling necessary
 * default attributes
 *
 * @author LiudchykValeriya
 * @see ActionCommand
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
