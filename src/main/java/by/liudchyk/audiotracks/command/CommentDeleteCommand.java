package by.liudchyk.audiotracks.command;

import by.liudchyk.audiotracks.entity.Comment;
import by.liudchyk.audiotracks.entity.Track;
import by.liudchyk.audiotracks.exception.LogicException;
import by.liudchyk.audiotracks.logic.CommentLogic;
import by.liudchyk.audiotracks.logic.UserLogic;
import by.liudchyk.audiotracks.manager.ConfigurationManager;
import by.liudchyk.audiotracks.manager.LanguageManager;
import by.liudchyk.audiotracks.servlet.SessionRequestContent;

import java.util.ArrayList;

/**
 * Created by Admin on 03.01.2017.
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
        Track track = (Track) requestContent.getSessionAttribute(TRACK_ATTRIBUTE);
        String date = requestContent.getParameter(DATE_PARAMETER);
        String nickname = requestContent.getParameter(USER_NAME_ATTRIBUTE);
        UserLogic userLogic = new UserLogic();
        try {
            int userId = userLogic.findUserByLogin(nickname).getId();
            int trackId = track.getId();
            CommentLogic commentLogic = new CommentLogic();
            boolean isDeleted = commentLogic.deleteComment(userId,trackId,date);
            if(isDeleted){
                ArrayList<Comment> comments = (ArrayList<Comment>) requestContent.getSessionAttribute(COMMENTS_ATTRIBUTE);
                Comment deleted = null;
                for (Comment temp:comments){
                    if(date.equals(temp.getDate()) && nickname.equals(temp.getUser())){
                        deleted = temp;
                    }
                }
                comments.remove(deleted);
                requestContent.setSessionAttribute(COMMENTS_ATTRIBUTE, comments);
                page = ConfigurationManager.getProperty(PATH_TRACK);
            }else{
                String message = LanguageManager.getProperty(DELETE_COMMENT, (String) requestContent.getSessionAttribute(PARAMETER));
                requestContent.setAttribute(MISTAKE_ATTRIBUTE, message);
                page = ConfigurationManager.getProperty((String) requestContent.getSessionAttribute(PATH_ATTRIBUTE));
            }
        }catch (LogicException e){
            LOG.error(e);
            requestContent.setAttribute(ERROR_MSG_ATTRIBUTE, e.getMessage());
            page = ConfigurationManager.getProperty(ERROR_PATH);
        }
        return page;
    }
}
