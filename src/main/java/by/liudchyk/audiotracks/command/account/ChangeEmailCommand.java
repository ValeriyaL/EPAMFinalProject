package by.liudchyk.audiotracks.command.account;

import by.liudchyk.audiotracks.command.ActionCommand;
import by.liudchyk.audiotracks.entity.User;
import by.liudchyk.audiotracks.exception.LogicException;
import by.liudchyk.audiotracks.logic.UserLogic;
import by.liudchyk.audiotracks.manager.ConfigurationManager;
import by.liudchyk.audiotracks.manager.MessageManager;
import by.liudchyk.audiotracks.servlet.SessionRequestContent;

/**
 * Class {@code ChangeEmailCommand} is used to change
 * user's email
 *
 * @author LiudchykValeriya
 * @see ActionCommand
 */
public class ChangeEmailCommand extends ActionCommand {
    private final String NAME_PARAM = "email";
    private final String SUCCESS_MESSAGE = "message.success.change.email";
    private final String SUCCESS_PATH = "path.page.account";

    @Override
    public String execute(SessionRequestContent requestContent) {
        String page;
        UserLogic userLogic = new UserLogic();
        String newEmail = requestContent.getParameter(NAME_PARAM);
        String logined = (String) requestContent.getSessionAttribute(IS_LOGIN_ATTRIBUTE);
        if (TRUE.equals(logined)) {
            try {
                User tempUser = (User) requestContent.getSessionAttribute(USER_ATTRIBUTE);
                String msgPath = userLogic.changeUserEmail(newEmail, tempUser.getId());
                if (SUCCESS_MESSAGE.equals(msgPath)) {
                    tempUser.setEmail(newEmail);
                    requestContent.setSessionAttribute(USER_ATTRIBUTE, tempUser);
                    String message = messageManager.getProperty(SUCCESS_MESSAGE, (String) requestContent.getSessionAttribute(PARAMETER));
                    requestContent.setAttribute(SUCCESS_ATTRIBUTE, message);
                    page = ConfigurationManager.getProperty(SUCCESS_PATH);
                } else {
                    requestContent.setAttribute(NAME_PARAM, newEmail);
                    String message = messageManager.getProperty(msgPath, (String) requestContent.getSessionAttribute(PARAMETER));
                    requestContent.setAttribute(MISTAKE_ATTRIBUTE, message);
                    page = ConfigurationManager.getProperty((String) requestContent.getSessionAttribute(PATH_ATTRIBUTE));
                }
            } catch (LogicException e) {
                page = redirectToErrorPage(requestContent, e);
            }
        } else {
            page = redirectToMain(requestContent);
        }
        return page;
    }
}
