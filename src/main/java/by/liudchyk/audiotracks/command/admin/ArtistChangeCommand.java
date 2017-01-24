package by.liudchyk.audiotracks.command.admin;

import by.liudchyk.audiotracks.command.ActionCommand;
import by.liudchyk.audiotracks.exception.LogicException;
import by.liudchyk.audiotracks.logic.TrackLogic;
import by.liudchyk.audiotracks.manager.ConfigurationManager;
import by.liudchyk.audiotracks.manager.MessageManager;
import by.liudchyk.audiotracks.servlet.SessionRequestContent;

/**
 * Class {@code ArtistChangeCommand} is used to change
 * track's artist
 *
 * @author LiudchykValeriya
 * @see ActionCommand
 */
public class ArtistChangeCommand extends ActionCommand {
    private final String TRACK_ID_PARAM = "trackId";
    private final String ARTIST_PARAM = "artist";
    private final String SUCCESS_MESSAGE = "message.success.change.artist";
    private final String ERROR_MESSAGE = "message.error.change.artist";

    @Override
    public String execute(SessionRequestContent requestContent) {
        String page;
        String role = (String) requestContent.getSessionAttribute(ROLE_ATTRIBUTE);
        if (ADMIN.equals(role)) {
            try {
                int trackId = Integer.valueOf((String) requestContent.getSessionAttribute(TRACK_ID_PARAM));
                TrackLogic trackLogic = new TrackLogic();
                String newArtist = requestContent.getParameter(ARTIST_PARAM);
                String msgPath = trackLogic.changeTrackArtist(newArtist, trackId);
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
