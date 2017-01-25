package by.liudchyk.audiotracks.command.admin;

import by.liudchyk.audiotracks.command.ActionCommand;
import by.liudchyk.audiotracks.exception.LogicException;
import by.liudchyk.audiotracks.logic.TrackLogic;
import by.liudchyk.audiotracks.manager.ConfigurationManager;
import by.liudchyk.audiotracks.manager.MessageManager;
import by.liudchyk.audiotracks.servlet.SessionRequestContent;

/**
 * Class {@code GenreChangeCommand} is used to change
 * track's genre
 *
 * @author LiudchykValeriya
 * @see ActionCommand
 */
public class GenreChangeCommand extends ActionCommand {
    private final String TRACK_ID_PARAM = "trackId";
    private final String GENRE_PARAM = "genre";
    private final String SUCCESS_MESSAGE = "message.success.change.genre";
    private final String ERROR_MESSAGE = "message.error.change.genre";

    @Override
    public String execute(SessionRequestContent requestContent) {
        String page;
        try {
            String role = (String) requestContent.getSessionAttribute(ROLE_ATTRIBUTE);
            if (ADMIN.equals(role)) {
                try {
                    int trackId = Integer.valueOf((String) requestContent.getSessionAttribute(TRACK_ID_PARAM));
                    TrackLogic trackLogic = new TrackLogic();
                    String newGenre = requestContent.getParameter(GENRE_PARAM);
                    String msgPath = trackLogic.changeTrackGenre(newGenre, trackId);
                    page = redirectAfterChanges(trackId, msgPath, requestContent, SUCCESS_MESSAGE);
                } catch (LogicException e) {
                    String message = messageManager.getProperty(ERROR_MESSAGE, (String) requestContent.getSessionAttribute(PARAMETER));
                    requestContent.setAttribute(MISTAKE_ATTRIBUTE, message);
                    page = ConfigurationManager.getProperty((String) requestContent.getSessionAttribute(PATH_ATTRIBUTE));
                }
            } else {
                page = redirectToMain(requestContent);
            }
        } catch (NumberFormatException e) {
            LOG.error("Wrong format in trackId parameter", e);
            page = redirectToErrorPageWithMessage(requestContent, "Wrong format in trackId parameter");

        }
        return page;
    }
}
