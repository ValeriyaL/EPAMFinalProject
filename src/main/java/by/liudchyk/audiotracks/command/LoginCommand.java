package by.liudchyk.audiotracks.command;

import by.liudchyk.audiotracks.entity.User;
import by.liudchyk.audiotracks.exception.LogicException;
import by.liudchyk.audiotracks.logic.LoginLogic;
import by.liudchyk.audiotracks.logic.UserLogic;
import by.liudchyk.audiotracks.manager.ConfigurationManager;
import by.liudchyk.audiotracks.manager.MessageManager;
import by.liudchyk.audiotracks.servlet.SessionRequestContent;

/**
 * Class {@code LoginCommand} is used to login user in a system
 *
 * @author LiudchykValeriya
 * @see ActionCommand
 */
public class LoginCommand extends ActionCommand {
    private final String NAME_PARAM = "nickname";
    private final String PASSWORD_PARAM = "password";
    private final String PATH = "path.page.main";
    private final String MESSAGE = "message.error.login";

    @Override
    public String execute(SessionRequestContent requestContent) {
        String page;
        LoginLogic loginLogic = new LoginLogic();
        String name = requestContent.getParameter(NAME_PARAM);
        String password = requestContent.getParameter(PASSWORD_PARAM);
        try {
            if (loginLogic.checkLogin(name, password)) {
                page = ConfigurationManager.getProperty(PATH);
                requestContent.setSessionAttribute(IS_LOGIN_ATTRIBUTE, TRUE);
                UserLogic userLogic = new UserLogic();
                User user = userLogic.findUserByLogin(name);
                requestContent.setSessionAttribute(USER_ATTRIBUTE, user);
                if (user.getStatus() == 0) {
                    requestContent.setSessionAttribute(ROLE_ATTRIBUTE, ADMIN);
                }
            } else {
                requestContent.setAttribute(NAME_PARAM, name);
                requestContent.setAttribute(PASSWORD_PARAM, password);
                String message = messageManager.getProperty(MESSAGE, (String) requestContent.getSessionAttribute(PARAMETER));
                requestContent.setAttribute(MISTAKE_ATTRIBUTE, message);
                page = ConfigurationManager.getProperty((String) requestContent.getSessionAttribute(PATH_ATTRIBUTE));
            }
        } catch (LogicException e) {
            page = redirectToErrorPage(requestContent, e);
        }
        return page;
    }
}
