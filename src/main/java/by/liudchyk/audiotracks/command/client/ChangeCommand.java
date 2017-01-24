package by.liudchyk.audiotracks.command.client;

import by.liudchyk.audiotracks.command.ActionCommand;
import by.liudchyk.audiotracks.manager.ConfigurationManager;
import by.liudchyk.audiotracks.servlet.SessionRequestContent;

/**
 * Class {@code ChangeCommand} is used to redirect to
 * changing page
 *
 * @author LiudchykValeriya
 * @see ActionCommand
 */
public class ChangeCommand extends ActionCommand {
    private final String CHANGE_PARAM = "change";
    private final String CHANGES_PATH = "path.page.changes";
    private final String TRACK_ID_PARAM = "trackId";

    @Override
    public String execute(SessionRequestContent requestContent) {
        String logined = (String) requestContent.getSessionAttribute(IS_LOGIN_ATTRIBUTE);
        if (TRUE.equals(logined)) {
            String changeCommand = requestContent.getParameter(CHANGE_PARAM);
            if (requestContent.isParameter(TRACK_ID_PARAM)) {
                requestContent.setSessionAttribute(TRACK_ID_PARAM, requestContent.getParameter(TRACK_ID_PARAM));
            } else {
                requestContent.setSessionAttribute(TRACK_ID_PARAM, null);
            }
            requestContent.setSessionAttribute(CHANGE_PARAM, changeCommand);
            return ConfigurationManager.getProperty(CHANGES_PATH);
        } else {
            return redirectToMain(requestContent);
        }
    }
}
