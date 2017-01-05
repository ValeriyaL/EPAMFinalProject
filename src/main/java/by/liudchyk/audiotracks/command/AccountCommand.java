package by.liudchyk.audiotracks.command;

import by.liudchyk.audiotracks.manager.ConfigurationManager;
import by.liudchyk.audiotracks.servlet.SessionRequestContent;

/**
 * Created by Admin on 25.12.2016.
 */
public class AccountCommand extends ActionCommand {
    private final String CHANGE_PARAM = "change";
    private final String CHANGES_PATH = "path.page.changes";
    private final String TRACK_ID_PARAM = "trackId";

    @Override
    public String execute(SessionRequestContent requestContent) {
        String changeCommand = requestContent.getParameter(CHANGE_PARAM);
        if(requestContent.isParameter(TRACK_ID_PARAM)) {
            requestContent.setSessionAttribute(TRACK_ID_PARAM, requestContent.getParameter(TRACK_ID_PARAM));
        }else{
            requestContent.setSessionAttribute(TRACK_ID_PARAM, null);
        }
        requestContent.setSessionAttribute(CHANGE_PARAM, changeCommand);
        return ConfigurationManager.getProperty(CHANGES_PATH);
    }
}
