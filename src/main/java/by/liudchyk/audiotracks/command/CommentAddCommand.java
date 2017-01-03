package by.liudchyk.audiotracks.command;

import by.liudchyk.audiotracks.entity.Comment;
import by.liudchyk.audiotracks.entity.Track;
import by.liudchyk.audiotracks.entity.User;
import by.liudchyk.audiotracks.exception.LogicException;
import by.liudchyk.audiotracks.logic.CommentLogic;
import by.liudchyk.audiotracks.manager.ConfigurationManager;
import by.liudchyk.audiotracks.manager.LanguageManager;
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
    private final String DATE_PATTERN = "y-MM-DD HH:mm:ss";

    @Override
    public String execute(SessionRequestContent requestContent) {
        String path;
        User tempUser = (User)requestContent.getSessionAttribute(USER_ATTRIBUTE);
        Track tempTrack = (Track)requestContent.getSessionAttribute(TRACK_ATTRIBUTE);
        String text = requestContent.getParameter(COMMENT_TEXT_ATTRIBUTE);
        int userId = tempUser.getId();
        int trackId = tempTrack.getId();
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat(DATE_PATTERN);
        String df = format.format(date);
        CommentLogic commentLogic = new CommentLogic();
        String errCom = commentLogic.addComment(date, text, userId, trackId);
        if(errCom.isEmpty()) {
            ArrayList<Comment> comments = (ArrayList<Comment>) requestContent.getSessionAttribute(COMMENTS_ATTRIBUTE);
            Comment comment = new Comment(text, tempUser.getNickname(), df);
            comments.add(comment);
            requestContent.setSessionAttribute(COMMENTS_ATTRIBUTE, comments);
            path = ConfigurationManager.getProperty(PATH_TRACK);
        }else{
            String message = LanguageManager.getProperty(errCom, (String) requestContent.getSessionAttribute(PARAMETER));
            requestContent.setAttribute(MISTAKE_ATTRIBUTE, message);
            path = ConfigurationManager.getProperty((String) requestContent.getSessionAttribute(PATH_ATTRIBUTE));
        }
        return path;
    }
}
