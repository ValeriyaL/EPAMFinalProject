package by.liudchyk.audiotracks.command.account;

import by.liudchyk.audiotracks.command.ActionCommand;
import by.liudchyk.audiotracks.entity.User;
import by.liudchyk.audiotracks.exception.LogicException;
import by.liudchyk.audiotracks.logic.UserLogic;
import by.liudchyk.audiotracks.manager.ConfigurationManager;
import by.liudchyk.audiotracks.manager.MessageManager;
import by.liudchyk.audiotracks.servlet.SessionRequestContent;

/**
 * Created by Admin on 27.12.2016.
 */
public class ChangePasswordCommand extends ActionCommand {
    private final String OLD_PASSWORD_PARAM = "passwordOld";
    private final String NEW_PASSWORD_PARAM = "passwordNew";
    private final String CONFIRM_NEW_PASSWORD_PARAM = "passwordNewConfirm";
    private final String SUCCESS_MESSAGE = "message.success.change.password";
    private final String SUCCESS_PATH = "path.page.account";

    @Override
    public String execute(SessionRequestContent requestContent) {
        String page;
        UserLogic userLogic = new UserLogic();
        String passwordOld = requestContent.getParameter(OLD_PASSWORD_PARAM);
        String passwordNew = requestContent.getParameter(NEW_PASSWORD_PARAM);
        String passwordNewConfirm = requestContent.getParameter(CONFIRM_NEW_PASSWORD_PARAM);
        try {
            User tempUser = (User) requestContent.getSessionAttribute(USER_ATTRIBUTE);
            String msgPath = userLogic.changeUserPassword(passwordOld, passwordNew, passwordNewConfirm, tempUser.getId());
            if (SUCCESS_MESSAGE.equals(msgPath)) {
                tempUser.setPassword(passwordNew);
                requestContent.setSessionAttribute(USER_ATTRIBUTE, tempUser);
                String message = MessageManager.getProperty(SUCCESS_MESSAGE, (String) requestContent.getSessionAttribute(PARAMETER));
                requestContent.setAttribute(SUCCESS_ATTRIBUTE, message);
                page = ConfigurationManager.getProperty(SUCCESS_PATH);
            } else {
                requestContent.setAttribute(OLD_PASSWORD_PARAM, passwordOld);
                requestContent.setAttribute(NEW_PASSWORD_PARAM, passwordNew);
                requestContent.setAttribute(CONFIRM_NEW_PASSWORD_PARAM, passwordNewConfirm);
                String message = MessageManager.getProperty(msgPath, (String) requestContent.getSessionAttribute(PARAMETER));
                requestContent.setAttribute(MISTAKE_ATTRIBUTE, message);
                page = ConfigurationManager.getProperty((String) requestContent.getSessionAttribute(PATH_ATTRIBUTE));
            }
        } catch (LogicException e) {
            page = redirectToErrorPage(requestContent,e);
        }
        return page;
    }
}
