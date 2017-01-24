package by.liudchyk.audiotracks.command.admin;

import by.liudchyk.audiotracks.command.ActionCommand;
import by.liudchyk.audiotracks.exception.LogicException;
import by.liudchyk.audiotracks.logic.TrackLogic;
import by.liudchyk.audiotracks.manager.ConfigurationManager;
import by.liudchyk.audiotracks.manager.MessageManager;
import by.liudchyk.audiotracks.servlet.SessionRequestContent;

/**
 * Class {@code ChangePriceCommand} is used to change
 * track's price
 *
 * @author LiudchykValeriya
 * @see ActionCommand
 */
public class ChangePriceCommand extends ActionCommand {
    private final String PRICE_PARAM = "price";
    private final String ERROR_MESSAGE = "message.error.account.price";
    private final String TRACK_ID_PARAM = "trackId";
    private final String SUCCESS_MESSAGE = "message.success.change.price";

    @Override
    public String execute(SessionRequestContent requestContent) {
        String page;
        String role = (String) requestContent.getSessionAttribute(ROLE_ATTRIBUTE);
        if (ADMIN.equals(role)) {
            try {
                int trackId = Integer.valueOf((String) requestContent.getSessionAttribute(TRACK_ID_PARAM));
                TrackLogic trackLogic = new TrackLogic();
                String newPrice = requestContent.getParameter(PRICE_PARAM);
                String msgPath = trackLogic.changeTrackPrice(newPrice, trackId);
                page = redirectAfterChanges(trackId, msgPath, requestContent, SUCCESS_MESSAGE);
            } catch (LogicException e) {
                String message = MessageManager.getProperty(ERROR_MESSAGE, (String) requestContent.getSessionAttribute(PARAMETER));
                requestContent.setAttribute(MISTAKE_ATTRIBUTE, message);
                page = ConfigurationManager.getProperty((String) requestContent.getSessionAttribute(PATH_ATTRIBUTE));
            }
        } else {
            page = redirectToMain(requestContent);
        }
        return page;
    }
}
