package by.liudchyk.audiotracks.command;

import by.liudchyk.audiotracks.entity.Comment;
import by.liudchyk.audiotracks.entity.Track;
import by.liudchyk.audiotracks.exception.LogicException;
import by.liudchyk.audiotracks.logic.TrackLogic;
import by.liudchyk.audiotracks.manager.ConfigurationManager;
import by.liudchyk.audiotracks.manager.MessageManager;
import by.liudchyk.audiotracks.servlet.SessionRequestContent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

public abstract class ActionCommand {
    public static final Logger LOG = LogManager.getLogger();
    static final String ERROR_MSG_ATTRIBUTE = "errorMessage";
    public static final String USER_ATTRIBUTE = "user";
    public static final String SUCCESS_ATTRIBUTE = "info";
    public static final String MISTAKE_ATTRIBUTE = "mistake";
    public static final String PATH_ATTRIBUTE = "page";
    static final String ERROR_PATH = "path.page.error";
    public static final String PARAMETER = "locale";
    static final String TRUE = "true";
    static final String FALSE = "false";
    static final String PATH_MAIN = "path.page.main";
    static final String TRACKS_ATTRIBUTE = "tracks";
    private final String TRACK_ATTRIBUTE = "trackInfo";
    private final String COMMENTS_ATTRIBUTE = "comments";
    private final String PAGE_TRACK_PATH = "path.page.track";

    public abstract String execute(SessionRequestContent requestContent);

    public String redirectToErrorPage(SessionRequestContent requestContent, Exception e) {
        LOG.error(e);
        requestContent.setAttribute(ERROR_MSG_ATTRIBUTE, e.getMessage());
        return ConfigurationManager.getProperty(ERROR_PATH);
    }

    public String redirectToMain(SessionRequestContent requestContent) {
        String page;
        ArrayList<Track> tracks;
        TrackLogic trackLogic = new TrackLogic();
        try {
            tracks = trackLogic.findMostOrdered();
            requestContent.setSessionAttribute(TRACKS_ATTRIBUTE, tracks);
            page = ConfigurationManager.getProperty(PATH_MAIN);
        } catch (LogicException e) {
            page = redirectToErrorPage(requestContent, e);
        }
        return page;
    }

    public String redirectToTrackPage(int trackId, SessionRequestContent requestContent) throws LogicException {
        TrackLogic trackLogic = new TrackLogic();
        ArrayList<Comment> comments = trackLogic.findAllCommentsById(trackId);
        Track track = trackLogic.findTrackById(trackId);
        requestContent.setSessionAttribute(TRACK_ATTRIBUTE, track);
        requestContent.setSessionAttribute(COMMENTS_ATTRIBUTE, comments);
        return ConfigurationManager.getProperty(PAGE_TRACK_PATH);
    }

    public String redirectAfterChanges(int trackId, String msgPath, SessionRequestContent requestContent, final String SUCCESS_MESSAGE) throws LogicException {
        String page;
        if (msgPath.isEmpty()) {
            String message = MessageManager.getProperty(SUCCESS_MESSAGE, (String) requestContent.getSessionAttribute(PARAMETER));
            requestContent.setAttribute(SUCCESS_ATTRIBUTE, message);
            page = redirectToTrackPage(trackId, requestContent);
        } else {
            String message = MessageManager.getProperty(msgPath, (String) requestContent.getSessionAttribute(PARAMETER));
            requestContent.setAttribute(MISTAKE_ATTRIBUTE, message);
            page = ConfigurationManager.getProperty((String) requestContent.getSessionAttribute(PATH_ATTRIBUTE));
        }
        return page;
    }
}
