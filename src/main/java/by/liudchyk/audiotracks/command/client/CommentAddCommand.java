package by.liudchyk.audiotracks.command.client;

import by.liudchyk.audiotracks.command.ActionCommand;
import by.liudchyk.audiotracks.entity.Comment;
import by.liudchyk.audiotracks.entity.Track;
import by.liudchyk.audiotracks.entity.User;
import by.liudchyk.audiotracks.exception.LogicException;
import by.liudchyk.audiotracks.logic.CommentLogic;
import by.liudchyk.audiotracks.logic.TrackLogic;
import by.liudchyk.audiotracks.manager.ConfigurationManager;
import by.liudchyk.audiotracks.manager.MessageManager;
import by.liudchyk.audiotracks.servlet.SessionRequestContent;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Admin on 03.01.2017.
 */
public class CommentAddCommand extends ActionCommand {
    private final String PATH_TRACK = "path.page.track";
    private final String COMMENTS_ATTRIBUTE = "comments";
    private final String TRACK_ATTRIBUTE = "trackInfo";
    private final String COMMENT_TEXT_ATTRIBUTE = "text";

    @Override
    public String execute(SessionRequestContent requestContent) {
        String page;
        User tempUser = (User)requestContent.getSessionAttribute(USER_ATTRIBUTE);
        Track tempTrack = (Track)requestContent.getSessionAttribute(TRACK_ATTRIBUTE);
        String text = requestContent.getParameter(COMMENT_TEXT_ATTRIBUTE);
        int userId = tempUser.getId();
        int trackId = tempTrack.getId();
        Date date = new Date();
        try {
            CommentLogic commentLogic = new CommentLogic();
            TrackLogic trackLogic = new TrackLogic();
            String errCom = commentLogic.addComment(date, text, userId, trackId);
            if (errCom.isEmpty()) {
                ArrayList<Comment> comments = trackLogic.findAllCommentsById(trackId);
                requestContent.setSessionAttribute(COMMENTS_ATTRIBUTE, comments);
                page = ConfigurationManager.getProperty(PATH_TRACK);
            } else {
                String message = MessageManager.getProperty(errCom, (String) requestContent.getSessionAttribute(PARAMETER));
                requestContent.setAttribute(MISTAKE_ATTRIBUTE, message);
                page = ConfigurationManager.getProperty((String) requestContent.getSessionAttribute(PATH_ATTRIBUTE));
            }
        }catch (LogicException e){
            page = redirectToErrorPage(requestContent,e);
        }
        return page;
    }
}
