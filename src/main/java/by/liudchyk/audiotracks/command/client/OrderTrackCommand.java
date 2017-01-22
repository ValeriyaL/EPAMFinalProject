package by.liudchyk.audiotracks.command.client;

import by.liudchyk.audiotracks.command.OrderCommand;
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
 * Class {@code OrderTrackCommand} is used to redirect to page with orders if
 * order already exists or redirect to page with confirm order
 *
 * @author LiudchykValeriya
 * @see OrderCommand
 */
public class OrderTrackCommand extends OrderCommand {
    private final String TRACK_ID_ATTRIBUTE = "trackId";
    private final String ORDERED_MESSAGE = "message.track.ordered";
    private final String BUY_PATH = "path.page.buy";
    private final String PRICE_BONUS_ATTR = "bonusPrice";
    private final String TRACK_ATTRIBUTE = "trackInfo";

    @Override
    public String execute(SessionRequestContent requestContent) {
        String page;
        User user = (User) requestContent.getSessionAttribute(USER_ATTRIBUTE);
        int trackId = Integer.valueOf(requestContent.getParameter(TRACK_ID_ATTRIBUTE));
        OrderLogic orderLogic = new OrderLogic();
        try {
            boolean isOrdered = orderLogic.isOrderExist(user.getId(), trackId);
            if (isOrdered) {
                String message = MessageManager.getProperty(ORDERED_MESSAGE, (String) requestContent.getSessionAttribute(PARAMETER));
                requestContent.setAttribute(SUCCESS_ATTRIBUTE, message);
                page = userTracks(requestContent);
            } else {
                UserLogic userLogic = new UserLogic();
                TrackLogic trackLogic = new TrackLogic();
                Track track = trackLogic.findTrackById(trackId);
                double price = track.getPrice();
                int bonus = userLogic.findBonusById(user.getId());
                double bonusPrice = trackLogic.calculateBonusPrice(price, bonus);
                requestContent.setSessionAttribute(PRICE_BONUS_ATTR, bonusPrice);
                requestContent.setSessionAttribute(TRACK_ATTRIBUTE, track);
                page = ConfigurationManager.getProperty(BUY_PATH);
            }
        } catch (LogicException e) {
            page = redirectToErrorPage(requestContent, e);
        }
        return page;
    }
}
