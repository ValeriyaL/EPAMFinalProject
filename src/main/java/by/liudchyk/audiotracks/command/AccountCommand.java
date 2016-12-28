package by.liudchyk.audiotracks.command;

import by.liudchyk.audiotracks.manager.ConfigurationManager;
import by.liudchyk.audiotracks.servlet.SessionRequestContent;

/**
 * Created by Admin on 25.12.2016.
 */
public class AccountCommand implements ActionCommand {
    private final String PATH_ATTRIBUTE = "page";
    private final String CHANGE_PARAM = "change";
    private final String CHANGES_PATH = "path.page.changes";

    @Override
    public String execute(SessionRequestContent requestContent) {
        String page = null;
        String changeCommand = requestContent.getParameter(CHANGE_PARAM);
        page = ConfigurationManager.getProperty(CHANGES_PATH);
        requestContent.setSessionAttribute(CHANGE_PARAM, changeCommand);
        return page;
    }
}
