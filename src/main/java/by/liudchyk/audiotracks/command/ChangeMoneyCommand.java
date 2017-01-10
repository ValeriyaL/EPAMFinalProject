package by.liudchyk.audiotracks.command;

import by.liudchyk.audiotracks.entity.User;
import by.liudchyk.audiotracks.exception.LogicException;
import by.liudchyk.audiotracks.logic.UserLogic;
import by.liudchyk.audiotracks.manager.ConfigurationManager;
import by.liudchyk.audiotracks.manager.MessageManager;
import by.liudchyk.audiotracks.servlet.SessionRequestContent;

/**
 * Created by Admin on 27.12.2016.
 */
public class ChangeMoneyCommand extends ActionCommand {
    private final String NAME_PARAM = "money";
    private final String SUCCESS_MESSAGE = "message.success.change.money";
    private final String ERROR_MESSAGE = "message.error.change.money";
    private final String SUCCESS_PATH = "path.page.account";

    @Override
    public String execute(SessionRequestContent requestContent) {
        String page = null;
        try {
            User tempUser = (User) requestContent.getSessionAttribute(USER_ATTRIBUTE);
            UserLogic userLogic = new UserLogic();
            double newMoney = Double.valueOf(requestContent.getParameter(NAME_PARAM));
            String msgPath = userLogic.changeUserMoney(newMoney + tempUser.getMoney(), tempUser.getId(), tempUser.getCardNumber());
            if (SUCCESS_MESSAGE.equals(msgPath)) {
                tempUser.setMoney(userLogic.findMoneyById(tempUser.getId()));
                requestContent.setSessionAttribute(USER_ATTRIBUTE, tempUser);
                String message = MessageManager.getProperty(SUCCESS_MESSAGE, (String) requestContent.getSessionAttribute(PARAMETER));
                requestContent.setAttribute(SUCCESS_ATTRIBUTE, message);
                page = ConfigurationManager.getProperty(SUCCESS_PATH);
            } else {
                String message = MessageManager.getProperty(msgPath, (String) requestContent.getSessionAttribute(PARAMETER));
                requestContent.setAttribute(MISTAKE_ATTRIBUTE, message);
                page = ConfigurationManager.getProperty((String) requestContent.getSessionAttribute(PATH_ATTRIBUTE));
            }
        } catch (LogicException | NumberFormatException e) {
            String message = MessageManager.getProperty(ERROR_MESSAGE, (String) requestContent.getSessionAttribute(PARAMETER));
            requestContent.setAttribute(MISTAKE_ATTRIBUTE, message);
            page = ConfigurationManager.getProperty((String) requestContent.getSessionAttribute(PATH_ATTRIBUTE));
        }
        return page;
    }
}
