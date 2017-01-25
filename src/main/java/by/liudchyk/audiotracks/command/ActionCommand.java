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

/**
 * Class {@code ActionCommand} is used as superclass of all commands.
 * Contain some common methods and abstract method execute.
 *
 * @author Liudchyk Valeriya
 */
public abstract class ActionCommand {
    public static final Logger LOG = LogManager.getLogger();
    public final MessageManager messageManager = new MessageManager();
    static final String ERROR_MSG_ATTRIBUTE = "errorMessage";
    public static final String USER_ATTRIBUTE = "user";
    public static final String SUCCESS_ATTRIBUTE = "info";
    public static final String MISTAKE_ATTRIBUTE = "mistake";
    public static final String PATH_ATTRIBUTE = "page";
    static final String ERROR_PATH = "path.page.error";
    public static final String PARAMETER = "locale";
    public static final String TRUE = "true";
    public static final String FALSE = "false";
    static final String PATH_MAIN = "path.page.main";
    static final String TRACKS_ATTRIBUTE = "tracks";
    private final String TRACK_ATTRIBUTE = "trackInfo";
    private final String COMMENTS_ATTRIBUTE = "comments";
    private final String PAGE_TRACK_PATH = "path.page.track";
    public static final String IS_LOGIN_ATTRIBUTE = "isLogin";
    public static final String ROLE_ATTRIBUTE = "role";
    public static final String ADMIN = "Admin";

    /**
     * Does all necessary work to handle command
     *
     * @param requestContent is content of request
     * @return path to page as a result of command
     */
    public abstract String execute(SessionRequestContent requestContent);

    public String redirectToErrorPage(SessionRequestContent requestContent, Exception e) {
        LOG.error(e);
        requestContent.setAttribute(ERROR_MSG_ATTRIBUTE, e.getMessage());
        return ConfigurationManager.getProperty(ERROR_PATH);
    }

    /**
     * Fills all necessary attributes and formed path to main page
     *
     * @param requestContent is content of request
     * @return path to main page
     */
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

    /**
     * Fills all necessary attributes and formed path to track page
     *
     * @param trackId        is track's id
     * @param requestContent is content of request
     * @return path to track page
     * @throws LogicException if trackLogic throws LogicException
     */
    public String redirectToTrackPage(int trackId, SessionRequestContent requestContent) throws LogicException {
        TrackLogic trackLogic = new TrackLogic();
        ArrayList<Comment> comments = trackLogic.findAllCommentsById(trackId);
        Track track = trackLogic.findTrackById(trackId);
        requestContent.setSessionAttribute(TRACK_ATTRIBUTE, track);
        requestContent.setSessionAttribute(COMMENTS_ATTRIBUTE, comments);
        return ConfigurationManager.getProperty(PAGE_TRACK_PATH);
    }

    /**
     * Fills all necessary attributes and formed path to page after changes
     *
     * @param trackId         is track's id
     * @param msgPath         is path to message page
     * @param requestContent  is content of request
     * @param SUCCESS_MESSAGE is success message
     * @return path to message page
     * @throws LogicException if redirectToTrackPage throws LogicException
     */
    public String redirectAfterChanges(int trackId, String msgPath, SessionRequestContent requestContent, final String SUCCESS_MESSAGE) throws LogicException {
        String page;
        if (msgPath.isEmpty()) {
            String message = messageManager.getProperty(SUCCESS_MESSAGE, (String) requestContent.getSessionAttribute(PARAMETER));
            requestContent.setAttribute(SUCCESS_ATTRIBUTE, message);
            page = redirectToTrackPage(trackId, requestContent);
        } else {
            String message = messageManager.getProperty(msgPath, (String) requestContent.getSessionAttribute(PARAMETER));
            requestContent.setAttribute(MISTAKE_ATTRIBUTE, message);
            page = ConfigurationManager.getProperty((String) requestContent.getSessionAttribute(PATH_ATTRIBUTE));
        }
        return page;
    }
}
