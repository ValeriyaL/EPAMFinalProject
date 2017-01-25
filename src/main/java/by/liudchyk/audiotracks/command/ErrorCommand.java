package by.liudchyk.audiotracks.command;

import by.liudchyk.audiotracks.manager.MessageManager;
import by.liudchyk.audiotracks.servlet.SessionRequestContent;

/**
 * Class {@code ErrorCommand} is used to redirect to error page
 * after error downloading
 *
 * @author LiudchykValeriya
 * @see ActionCommand
 */
public class ErrorCommand extends ActionCommand {
    private final String MSG_PATH = "message.error.download";

    @Override
    public String execute(SessionRequestContent requestContent) {
        String message = MessageManager.getProperty(MSG_PATH, (String) requestContent.getSessionAttribute(PARAMETER));
        Exception exception = new Exception(message);
        return redirectToErrorPage(requestContent, exception);
    }
}
