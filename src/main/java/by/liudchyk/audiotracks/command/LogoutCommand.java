package by.liudchyk.audiotracks.command;

import by.liudchyk.audiotracks.manager.ConfigurationManager;
import by.liudchyk.audiotracks.servlet.SessionRequestContent;

/**
 * Created by Admin on 24.12.2016.
 */
public class LogoutCommand extends ActionCommand {
    private final String IS_LOGIN_ATTRIBUTE = "isLogin";
    private final String NAME_PARAM = "nickname";
    private final String ACCOUNT_PATH = "path.page.account";
    private final String CHANGES_PATH = "path.page.changes";
    private final String MAIN_PATH = "path.page.main";
    private final String ORDERS_PATH = "path.page.orders";
    private final String ROLE_ATTRIBUTE = "role";
    private final String ERROR_PATH = "path.page.error";
    private final String MESSAGE_PATH = "path.page.message";
    private final String DELETED_PATH = "path.page.deleted";
    private final String INFO_PATH = "path.page.info";
    private final String BONUS_PATH = "path.page.bonus";
    private final String BUY_PATH = "path.page.buy";

    @Override
    public String execute(SessionRequestContent requestContent) {
        String page;
        requestContent.setSessionAttribute(IS_LOGIN_ATTRIBUTE, null);
        requestContent.setSessionAttribute(NAME_PARAM, null);
        requestContent.setSessionAttribute(USER_ATTRIBUTE, null);
        requestContent.setSessionAttribute(ROLE_ATTRIBUTE, null);
        if (ACCOUNT_PATH.equals(requestContent.getSessionAttribute(PATH_ATTRIBUTE)) ||
                CHANGES_PATH.equals(requestContent.getSessionAttribute(PATH_ATTRIBUTE)) ||
                ORDERS_PATH.equals(requestContent.getSessionAttribute(PATH_ATTRIBUTE)) ||
                ERROR_PATH.equals(requestContent.getSessionAttribute(PATH_ATTRIBUTE)) ||
                MESSAGE_PATH.equals(requestContent.getSessionAttribute(PATH_ATTRIBUTE)) ||
                DELETED_PATH.equals(requestContent.getSessionAttribute(PATH_ATTRIBUTE)) ||
                INFO_PATH.equals(requestContent.getSessionAttribute(PATH_ATTRIBUTE)) ||
                BONUS_PATH.equals(requestContent.getSessionAttribute(PATH_ATTRIBUTE)) ||
                BUY_PATH.equals(requestContent.getSessionAttribute(PATH_ATTRIBUTE))) {
            page = redirectToMain(requestContent);
        } else {
            page = ConfigurationManager.getProperty((String) requestContent.getSessionAttribute(PATH_ATTRIBUTE));
        }
        return page;
    }
}
