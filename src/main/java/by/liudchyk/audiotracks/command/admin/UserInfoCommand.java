package by.liudchyk.audiotracks.command.admin;

import by.liudchyk.audiotracks.command.ActionCommand;
import by.liudchyk.audiotracks.entity.User;
import by.liudchyk.audiotracks.exception.LogicException;
import by.liudchyk.audiotracks.logic.OrderLogic;
import by.liudchyk.audiotracks.logic.UserLogic;
import by.liudchyk.audiotracks.manager.ConfigurationManager;
import by.liudchyk.audiotracks.manager.MessageManager;
import by.liudchyk.audiotracks.servlet.SessionRequestContent;

/**
 * Class {@code UserInfoCommand} is used to represent user info
 *
 * @author LiudchykValeriya
 * @see ActionCommand
 */
public class UserInfoCommand extends ActionCommand {
    private final String USER_INFO_PATH = "path.page.info";
    private final String FIND_USER_NICK = "userNickname";
    private final String MESSAGE = "message.error.user.found";
    private final String NUM_OF_COMM_ATTR = "numOfComm";
    private final String NUM_OF_ORDERS_ATTR = "numOfOrders";
    private final String BONUS_ATTR = "bonus";

    @Override
    public String execute(SessionRequestContent requestContent) {
        String page;
        String nickname = requestContent.getParameter(FIND_USER_NICK);
        UserLogic userLogic = new UserLogic();
        OrderLogic orderLogic = new OrderLogic();
        try {
            User user = userLogic.findUserByLogin(nickname);
            if (user != null) {
                requestContent.setSessionAttribute(NUM_OF_COMM_ATTR, userLogic.findNumberOfCommentsById(user.getId()));
                requestContent.setSessionAttribute(NUM_OF_ORDERS_ATTR, orderLogic.findAllUserOrders(user).size());
                requestContent.setSessionAttribute(FIND_USER_NICK, user.getNickname());
                requestContent.setSessionAttribute(BONUS_ATTR, user.getBonus());
                page = ConfigurationManager.getProperty(USER_INFO_PATH);
            } else {
                String message = MessageManager.getProperty(MESSAGE, (String) requestContent.getSessionAttribute(PARAMETER));
                requestContent.setAttribute(MISTAKE_ATTRIBUTE, message);
                page = ConfigurationManager.getProperty((String) requestContent.getSessionAttribute(PATH_ATTRIBUTE));
            }
        } catch (LogicException e) {
            page = redirectToErrorPage(requestContent, e);
        }
        return page;
    }
}
