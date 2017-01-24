package by.liudchyk.audiotracks.command.admin;

import by.liudchyk.audiotracks.command.ActionCommand;
import by.liudchyk.audiotracks.entity.User;
import by.liudchyk.audiotracks.exception.LogicException;
import by.liudchyk.audiotracks.logic.UserLogic;
import by.liudchyk.audiotracks.manager.ConfigurationManager;
import by.liudchyk.audiotracks.servlet.SessionRequestContent;

import java.util.ArrayList;

/**
 * Class {@code AllUsersCommand} is used to represent all users
 *
 * @author LiudchykValeriya
 * @see ActionCommand
 */
public class AllUsersCommand extends ActionCommand {
    private final String USERS_PATH = "path.page.users";
    private final String USERS_ATTRIBUTE = "users";

    @Override
    public String execute(SessionRequestContent requestContent) {
        String page;
        String role = (String) requestContent.getSessionAttribute(ROLE_ATTRIBUTE);
        if (ADMIN.equals(role)) {
            UserLogic userLogic = new UserLogic();
            try {
                ArrayList<User> users = userLogic.findAllUsers();
                requestContent.setSessionAttribute(USERS_ATTRIBUTE, users);
                page = ConfigurationManager.getProperty(USERS_PATH);
            } catch (LogicException e) {
                page = redirectToErrorPage(requestContent, e);
            }
        } else {
            page = redirectToMain(requestContent);
        }
        return page;
    }
}
