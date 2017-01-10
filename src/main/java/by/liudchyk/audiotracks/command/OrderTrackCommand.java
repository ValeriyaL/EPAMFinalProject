package by.liudchyk.audiotracks.command;

import by.liudchyk.audiotracks.entity.Track;
import by.liudchyk.audiotracks.entity.User;
import by.liudchyk.audiotracks.exception.LogicException;
import by.liudchyk.audiotracks.logic.OrderLogic;
import by.liudchyk.audiotracks.logic.TrackLogic;
import by.liudchyk.audiotracks.logic.UserLogic;
import by.liudchyk.audiotracks.manager.ConfigurationManager;
import by.liudchyk.audiotracks.manager.MessageManager;
import by.liudchyk.audiotracks.servlet.SessionRequestContent;

/**
 * Created by Admin on 10.01.2017.
 */
public class OrderTrackCommand extends ActionCommand {
    private final String TRACK_ID_ATTRIBUTE = "trackId";
    private final String ORDERED_MESSAGE = "message.track.ordered";
    private final String BUY_PATH = "path.page.buy";
    private final String PRICE_BONUS_ATTR = "bonusPrice";
    private final String TRACK_ATTRIBUTE = "trackInfo";

    @Override
    public String execute(SessionRequestContent requestContent) {
        String page;
        User user = (User) requestContent.getSessionAttribute(USER_ATTRIBUTE);
        int trackId = Integer.valueOf( requestContent.getParameter(TRACK_ID_ATTRIBUTE));
        OrderLogic orderLogic = new OrderLogic();
        try {
            boolean isOrdered = orderLogic.isOrderExist(user.getId(), trackId);
            if (isOrdered) {
                String message = MessageManager.getProperty(ORDERED_MESSAGE, (String) requestContent.getSessionAttribute(PARAMETER));
                requestContent.setAttribute(SUCCESS_ATTRIBUTE, message);
                OrdersCommand ordersCommand = new OrdersCommand();
                page = ordersCommand.execute(requestContent);
            } else {
                UserLogic userLogic = new UserLogic();
                TrackLogic trackLogic = new TrackLogic();
                Track track = trackLogic.findTrackById(trackId);
                double price = track.getPrice();
                int bonus = userLogic.findBonusById(user.getId());
                double bonusPrice =trackLogic.calculateBonusPrice(price,bonus);
                requestContent.setSessionAttribute(PRICE_BONUS_ATTR, bonusPrice);
                requestContent.setSessionAttribute(TRACK_ATTRIBUTE,track);
                page = ConfigurationManager.getProperty(BUY_PATH);
            }
        }catch (LogicException e){
            LOG.error(e);
            requestContent.setAttribute(ERROR_MSG_ATTRIBUTE, e.getMessage());
            page = ConfigurationManager.getProperty(ERROR_PATH);
        }
        return page;
    }
}
