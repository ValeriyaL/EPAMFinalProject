package by.liudchyk.audiotracks.command.client;

import by.liudchyk.audiotracks.command.ActionCommand;
import by.liudchyk.audiotracks.entity.Comment;
import by.liudchyk.audiotracks.entity.Track;
import by.liudchyk.audiotracks.exception.LogicException;
import by.liudchyk.audiotracks.logic.CommentLogic;
import by.liudchyk.audiotracks.logic.TrackLogic;
import by.liudchyk.audiotracks.logic.UserLogic;
import by.liudchyk.audiotracks.manager.ConfigurationManager;
import by.liudchyk.audiotracks.manager.MessageManager;
import by.liudchyk.audiotracks.servlet.SessionRequestContent;

import java.util.ArrayList;

/**
 * Class {@code CommentDeleteCommand} is used to delete comment
 *
 * @author LiudchykValeriya
 * @see ActionCommand
 */
public class CommentDeleteCommand extends ActionCommand {
    private final String USER_NAME_ATTRIBUTE = "userName";
    private final String PATH_TRACK = "path.page.track";
    private final String TRACK_ATTRIBUTE = "trackInfo";
    private final String DATE_PARAMETER = "date";
    private final String DELETE_COMMENT = "message.error.comment.delete";
    private final String COMMENTS_ATTRIBUTE = "comments";

    @Override
    public String execute(SessionRequestContent requestContent) {
        String page;
        String logined = (String) requestContent.getSessionAttribute(IS_LOGIN_ATTRIBUTE);
        if (TRUE.equals(logined)) {
            Track track = (Track) requestContent.getSessionAttribute(TRACK_ATTRIBUTE);
            String date = requestContent.getParameter(DATE_PARAMETER);
            String nickname = requestContent.getParameter(USER_NAME_ATTRIBUTE);
            UserLogic userLogic = new UserLogic();
            TrackLogic trackLogic = new TrackLogic();
            try {
                int userId = userLogic.findUserByLogin(nickname).getId();
                int trackId = track.getId();
                CommentLogic commentLogic = new CommentLogic();
                boolean isDeleted = commentLogic.deleteComment(userId, trackId, date);
                if (isDeleted) {
                    ArrayList<Comment> comments = trackLogic.findAllCommentsById(trackId);
                    requestContent.setSessionAttribute(COMMENTS_ATTRIBUTE, comments);
                    page = ConfigurationManager.getProperty(PATH_TRACK);
                } else {
                    String message = MessageManager.getProperty(DELETE_COMMENT, (String) requestContent.getSessionAttribute(PARAMETER));
                    requestContent.setAttribute(MISTAKE_ATTRIBUTE, message);
                    page = ConfigurationManager.getProperty((String) requestContent.getSessionAttribute(PATH_ATTRIBUTE));
                }
            } catch (LogicException e) {
                page = redirectToErrorPage(requestContent, e);
            }
        } else {
            page = redirectToMain(requestContent);
        }
        return page;
    }
}
