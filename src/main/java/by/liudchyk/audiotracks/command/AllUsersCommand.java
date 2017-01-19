package by.liudchyk.audiotracks.command;

import by.liudchyk.audiotracks.entity.User;
import by.liudchyk.audiotracks.exception.LogicException;
import by.liudchyk.audiotracks.logic.UserLogic;
import by.liudchyk.audiotracks.manager.ConfigurationManager;
import by.liudchyk.audiotracks.servlet.SessionRequestContent;

import java.util.ArrayList;

/**
 * Created by Admin on 19.01.2017.
 */
public class AllUsersCommand extends ActionCommand {
    private final String USERS_PATH = "path.page.users";
    private final String USERS_ATTRIBUTE = "users";

    @Override
    public String execute(SessionRequestContent requestContent) {
        String page;
        UserLogic userLogic = new UserLogic();
        try {
            ArrayList<User> users = userLogic.findAllUsers();
            requestContent.setSessionAttribute(USERS_ATTRIBUTE, users);
            page = ConfigurationManager.getProperty(USERS_PATH);
        } catch (LogicException e) {
            page = redirectToErrorPage(requestContent, e);
        }
        return page;
    }
}
