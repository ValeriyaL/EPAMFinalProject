package by.liudchyk.audiotracks.command;

import by.liudchyk.audiotracks.manager.ConfigurationManager;
import by.liudchyk.audiotracks.servlet.SessionRequestContent;

/**
 * Created by Admin on 24.12.2016.
 */
public class LogoutCommand implements ActionCommand {
    private static final String PATH_ATTRIBUTE = "page";
    private final String IS_LOGIN_ATTRIBUTE = "isLogin";
    private final String NAME_PARAM = "nickname";
    private final String USER_ATTRIBUTE = "user";
    private final String ACCOUNT_PATH = "path.page.account";
    private final String CHANGES_PATH = "path.page.changes";
    private final String MAIN_PATH = "path.page.main";

    @Override
    public String execute(SessionRequestContent requestContent) {
        String page = null;
        requestContent.setSessionAttribute(IS_LOGIN_ATTRIBUTE, null);
        requestContent.setSessionAttribute(NAME_PARAM, null);
        requestContent.setSessionAttribute(USER_ATTRIBUTE, null);
        if(ACCOUNT_PATH.equals(requestContent.getSessionAttribute(PATH_ATTRIBUTE)) ||
                CHANGES_PATH.equals(requestContent.getSessionAttribute(PATH_ATTRIBUTE))){
            page = ConfigurationManager.getProperty(MAIN_PATH);
        }else {
            page = ConfigurationManager.getProperty((String) requestContent.getSessionAttribute(PATH_ATTRIBUTE));
        }
        return page;
    }
}
