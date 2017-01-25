package by.liudchyk.audiotracks.command.account;

import by.liudchyk.audiotracks.command.ActionCommand;
import by.liudchyk.audiotracks.entity.User;
import by.liudchyk.audiotracks.exception.LogicException;
import by.liudchyk.audiotracks.logic.UserLogic;
import by.liudchyk.audiotracks.manager.ConfigurationManager;
import by.liudchyk.audiotracks.manager.MessageManager;
import by.liudchyk.audiotracks.servlet.SessionRequestContent;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Class {@code ChangeMoneyCommand} is used to change
 * user's money
 *
 * @author LiudchykValeriya
 * @see ActionCommand
 */
public class ChangeMoneyCommand extends ActionCommand {
    private final String NAME_PARAM = "money";
    private final String SUCCESS_MESSAGE = "message.success.change.money";
    private final String ERROR_MESSAGE = "message.error.change.money";
    private final String SUCCESS_PATH = "path.page.account";

    @Override
    public String execute(SessionRequestContent requestContent) {
        String page;
        String role = (String) requestContent.getSessionAttribute(ROLE_ATTRIBUTE);
        if (ADMIN.equals(role)) {
            try {
                User tempUser = (User) requestContent.getSessionAttribute(USER_ATTRIBUTE);
                UserLogic userLogic = new UserLogic();
                double newMoney = Double.valueOf(requestContent.getParameter(NAME_PARAM));
                String msgPath = userLogic.changeUserMoney(new BigDecimal(newMoney + tempUser.getMoney()).setScale(3, RoundingMode.HALF_UP).doubleValue(), tempUser.getId(), tempUser.getCardNumber());
                if (SUCCESS_MESSAGE.equals(msgPath)) {
                    tempUser.setMoney(userLogic.findMoneyById(tempUser.getId()));
                    requestContent.setSessionAttribute(USER_ATTRIBUTE, tempUser);
                    String message = messageManager.getProperty(SUCCESS_MESSAGE, (String) requestContent.getSessionAttribute(PARAMETER));
                    requestContent.setAttribute(SUCCESS_ATTRIBUTE, message);
                    page = ConfigurationManager.getProperty(SUCCESS_PATH);
                } else {
                    String message = messageManager.getProperty(msgPath, (String) requestContent.getSessionAttribute(PARAMETER));
                    requestContent.setAttribute(MISTAKE_ATTRIBUTE, message);
                    page = ConfigurationManager.getProperty((String) requestContent.getSessionAttribute(PATH_ATTRIBUTE));
                }
            } catch (LogicException e) {
                String message = messageManager.getProperty(ERROR_MESSAGE, (String) requestContent.getSessionAttribute(PARAMETER));
                requestContent.setAttribute(MISTAKE_ATTRIBUTE, message);
                page = ConfigurationManager.getProperty((String) requestContent.getSessionAttribute(PATH_ATTRIBUTE));
            } catch (NumberFormatException e){
                LOG.error("Wrong format in price parameter",e);
                page = redirectToErrorPageWithMessage(requestContent, "Wrong format in price parameter");
            }
        } else {
            page = redirectToMain(requestContent);
        }
        return page;
    }
}
