package by.liudchyk.audiotracks.command.account;

import by.liudchyk.audiotracks.command.ActionCommand;
import by.liudchyk.audiotracks.entity.User;
import by.liudchyk.audiotracks.exception.LogicException;
import by.liudchyk.audiotracks.logic.UserLogic;
import by.liudchyk.audiotracks.manager.ConfigurationManager;
import by.liudchyk.audiotracks.manager.MessageManager;
import by.liudchyk.audiotracks.servlet.SessionRequestContent;

/**
 * Class {@code ChangeLoginCommand} is used to change
 * user's nickname
 *
 * @author LiudchykValeriya
 * @see ActionCommand
 */
public class ChangeLoginCommand extends ActionCommand {
    private final String NAME_PARAM = "nickname";
    private final String SUCCESS_MESSAGE = "message.success.change.login";
    private final String SUCCESS_PATH = "path.page.account";

    @Override
    public String execute(SessionRequestContent requestContent) {
        String page;
        UserLogic userLogic = new UserLogic();
        String newLogin = requestContent.getParameter(NAME_PARAM);
        String logined = (String) requestContent.getSessionAttribute(IS_LOGIN_ATTRIBUTE);
        if (TRUE.equals(logined)) {
            try {
                User tempUser = (User) requestContent.getSessionAttribute(USER_ATTRIBUTE);
                String msgPath = userLogic.changeUserLogin(newLogin, tempUser.getId());
                if (SUCCESS_MESSAGE.equals(msgPath)) {
                    tempUser.setNickname(newLogin);
                    requestContent.setSessionAttribute(USER_ATTRIBUTE, tempUser);
                    String message = messageManager.getProperty(SUCCESS_MESSAGE, (String) requestContent.getSessionAttribute(PARAMETER));
                    requestContent.setAttribute(SUCCESS_ATTRIBUTE, message);
                    page = ConfigurationManager.getProperty(SUCCESS_PATH);
                } else {
                    requestContent.setAttribute(NAME_PARAM, newLogin);
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
