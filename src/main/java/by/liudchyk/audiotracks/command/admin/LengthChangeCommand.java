package by.liudchyk.audiotracks.command.admin;

import by.liudchyk.audiotracks.command.ActionCommand;
import by.liudchyk.audiotracks.exception.LogicException;
import by.liudchyk.audiotracks.logic.TrackLogic;
import by.liudchyk.audiotracks.manager.ConfigurationManager;
import by.liudchyk.audiotracks.manager.MessageManager;
import by.liudchyk.audiotracks.servlet.SessionRequestContent;

/**
 * Class {@code LengthChangeCommand} is used to change
 * track's length
 *
 * @author LiudchykValeriya
 * @see ActionCommand
 */
public class LengthChangeCommand extends ActionCommand {
    private final String TRACK_ID_PARAM = "trackId";
    private final String LENGTH_PARAM = "length";
    private final String SUCCESS_MESSAGE = "message.success.change.length";
    private final String ERROR_MESSAGE = "message.error.change.length";

    @Override
    public String execute(SessionRequestContent requestContent) {
        String page;
        try {
            int trackId = Integer.valueOf((String) requestContent.getSessionAttribute(TRACK_ID_PARAM));
            TrackLogic trackLogic = new TrackLogic();
            String newLength = requestContent.getParameter(LENGTH_PARAM);
            String msgPath = trackLogic.changeTrackLength(newLength, trackId);
            page = redirectAfterChanges(trackId, msgPath, requestContent, SUCCESS_MESSAGE);
        } catch (LogicException e) {
            String message = MessageManager.getProperty(ERROR_MESSAGE, (String) requestContent.getSessionAttribute(PARAMETER));
            requestContent.setAttribute(MISTAKE_ATTRIBUTE, message);
            page = ConfigurationManager.getProperty((String) requestContent.getSessionAttribute(PATH_ATTRIBUTE));
        }
        return page;
    }
}
