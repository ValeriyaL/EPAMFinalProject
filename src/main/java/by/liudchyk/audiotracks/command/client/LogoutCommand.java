package by.liudchyk.audiotracks.command.client;

import by.liudchyk.audiotracks.command.ActionCommand;
import by.liudchyk.audiotracks.manager.ConfigurationManager;
import by.liudchyk.audiotracks.servlet.SessionRequestContent;

/**
 * Class {@code LogoutCommand} is used to logout user form system
 * and redirect to main page if necessary
 *
 * @author LiudchykValeriya
 * @see ActionCommand
 */
public class LogoutCommand extends ActionCommand {
    private final String NAME_PARAM = "nickname";
    private final String ACCOUNT_PATH = "path.page.account";
    private final String CHANGES_PATH = "path.page.changes";
    private final String ORDERS_PATH = "path.page.orders";
    private final String ERROR_PATH = "path.page.error";
    private final String MESSAGE_PATH = "path.page.message";
    private final String DELETED_PATH = "path.page.deleted";
    private final String INFO_PATH = "path.page.info";
    private final String BONUS_PATH = "path.page.bonus";
    private final String BUY_PATH = "path.page.buy";
    private final String USERS_PATH = "path.page.users";
    private final String ADD_PATH = "path.page.add";

    @Override
    public String execute(SessionRequestContent requestContent) {
        String page;
        requestContent.setSessionAttribute(IS_LOGIN_ATTRIBUTE, null);
        requestContent.setSessionAttribute(NAME_PARAM, null);
        requestContent.setSessionAttribute(USER_ATTRIBUTE, null);
        requestContent.setSessionAttribute(ROLE_ATTRIBUTE, null);
        String tempPath = (String) requestContent.getSessionAttribute(PATH_ATTRIBUTE);
        if (ACCOUNT_PATH.equals(tempPath) || CHANGES_PATH.equals(tempPath) ||
                ORDERS_PATH.equals(tempPath) || ERROR_PATH.equals(tempPath) ||
                MESSAGE_PATH.equals(tempPath) || DELETED_PATH.equals(tempPath) ||
                INFO_PATH.equals(tempPath) || BONUS_PATH.equals(tempPath) ||
                BUY_PATH.equals(tempPath) || USERS_PATH.equals(tempPath) ||
                ADD_PATH.equals(tempPath)) {
            page = redirectToMain(requestContent);
        } else {
            page = ConfigurationManager.getProperty((String) requestContent.getSessionAttribute(PATH_ATTRIBUTE));
        }
        return page;
    }
}
