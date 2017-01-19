package by.liudchyk.audiotracks.command;

import by.liudchyk.audiotracks.entity.User;
import by.liudchyk.audiotracks.exception.LogicException;
import by.liudchyk.audiotracks.logic.OrderLogic;
import by.liudchyk.audiotracks.logic.UserLogic;
import by.liudchyk.audiotracks.manager.ConfigurationManager;
import by.liudchyk.audiotracks.manager.MessageManager;
import by.liudchyk.audiotracks.servlet.SessionRequestContent;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Admin on 10.01.2017.
 */
public class BuyTrackCommand extends OrderCommand {
    private final String ORDERED_MESSAGE = "message.success.order.track";
    private final String TRACK_ID_ATTRIBUTE = "trackId";
    private final String BONUS_PRICE_ATTRIBUTE = "bonusPrice";
    private final String DATE_PATTERN = "y-MM-DD HH:mm:ss";
    private final String MESSAGE = "message.error.money.enough";

    @Override
    public String execute(SessionRequestContent requestContent) {
        String page;
        User user = (User) requestContent.getSessionAttribute(USER_ATTRIBUTE);
        int trackId = Integer.valueOf( requestContent.getParameter(TRACK_ID_ATTRIBUTE));
        double price = (Double) requestContent.getSessionAttribute(BONUS_PRICE_ATTRIBUTE);
        OrderLogic orderLogic = new OrderLogic();
        UserLogic userLogic = new UserLogic();
        try {
            boolean isValid = orderLogic.isBuyingValid(price, user.getId());
            if (isValid) {
                Date date = new Date();
                SimpleDateFormat format = new SimpleDateFormat(DATE_PATTERN);
                String formatDate = format.format(date);
                orderLogic.addOrder(trackId, price, user.getId(), formatDate);
                user.setMoney(userLogic.findMoneyById(user.getId()));
                requestContent.setSessionAttribute(USER_ATTRIBUTE,user);
                String message = MessageManager.getProperty(ORDERED_MESSAGE, (String) requestContent.getSessionAttribute(PARAMETER));
                requestContent.setAttribute(SUCCESS_ATTRIBUTE, message);
                page = tracksInOrder(requestContent);
            } else {
                String message = MessageManager.getProperty(MESSAGE, (String) requestContent.getSessionAttribute(PARAMETER));
                requestContent.setAttribute(MISTAKE_ATTRIBUTE, message);
                page = ConfigurationManager.getProperty((String) requestContent.getSessionAttribute(PATH_ATTRIBUTE));
            }
        }catch (LogicException e ){
            page = redirectToErrorPage(requestContent,e);
        }
        return page;
    }
}
