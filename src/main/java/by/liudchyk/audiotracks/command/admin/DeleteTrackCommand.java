package by.liudchyk.audiotracks.command.admin;

import by.liudchyk.audiotracks.command.ActionCommand;
import by.liudchyk.audiotracks.entity.Comment;
import by.liudchyk.audiotracks.entity.Track;
import by.liudchyk.audiotracks.exception.LogicException;
import by.liudchyk.audiotracks.logic.TrackLogic;
import by.liudchyk.audiotracks.manager.ConfigurationManager;
import by.liudchyk.audiotracks.manager.MessageManager;
import by.liudchyk.audiotracks.servlet.SessionRequestContent;

import java.util.ArrayList;

/**
 * Class {@code DeleteTrackCommand} is used to delete comment
 *
 * @author LiudchykValeriya
 * @see ActionCommand
 */
public class DeleteTrackCommand extends ActionCommand {
    private final String TRACK_ID_PARAMETER = "track";
    private final String MESSAGE_PATH = "path.page.message";
    private final String MESSAGE = "message.success.track.delete";

    @Override
    public String execute(SessionRequestContent requestContent) {
        String page;
        String role = (String) requestContent.getSessionAttribute(ROLE_ATTRIBUTE);
        if (ADMIN.equals(role)) {
            int trackId = Integer.valueOf(requestContent.getParameter(TRACK_ID_PARAMETER));
            TrackLogic trackLogic = new TrackLogic();
            try {
                trackLogic.deleteTrackById(trackId);
                String message = MessageManager.getProperty(MESSAGE, (String) requestContent.getSessionAttribute(PARAMETER));
                requestContent.setAttribute(SUCCESS_ATTRIBUTE, message);
                page = ConfigurationManager.getProperty(MESSAGE_PATH);
            } catch (LogicException e) {
                page = redirectToErrorPage(requestContent, e);
            }
        } else {
            page = redirectToMain(requestContent);
        }
        return page;
    }
}
