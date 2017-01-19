package by.liudchyk.audiotracks.command;

import by.liudchyk.audiotracks.entity.Track;
import by.liudchyk.audiotracks.exception.LogicException;
import by.liudchyk.audiotracks.logic.TrackLogic;
import by.liudchyk.audiotracks.manager.ConfigurationManager;
import by.liudchyk.audiotracks.servlet.SessionRequestContent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

public abstract class ActionCommand {
    static final Logger LOG = LogManager.getLogger();
    static final String ERROR_MSG_ATTRIBUTE = "errorMessage";
    static final String USER_ATTRIBUTE = "user";
    static final String SUCCESS_ATTRIBUTE = "info";
    static final String MISTAKE_ATTRIBUTE = "mistake";
    static final String PATH_ATTRIBUTE = "page";
    static final String ERROR_PATH = "path.page.error";
    static final String PARAMETER = "locale";
    static final String TRUE = "true";
    static final String FALSE = "false";
    static final String PATH_MAIN = "path.page.main";
    static final String TRACKS_ATTRIBUTE = "tracks";

    public abstract String execute(SessionRequestContent requestContent);

    public String redirectToErrorPage(SessionRequestContent requestContent, Exception e){
        LOG.error(e);
        requestContent.setAttribute(ERROR_MSG_ATTRIBUTE, e.getMessage());
        return ConfigurationManager.getProperty(ERROR_PATH);
    }

    public String redirectToMain(SessionRequestContent requestContent){
        String page;
        ArrayList<Track> tracks;
        TrackLogic trackLogic = new TrackLogic();
        try {
            tracks = trackLogic.findMostOrdered();
            requestContent.setSessionAttribute(TRACKS_ATTRIBUTE, tracks);
            page = ConfigurationManager.getProperty(PATH_MAIN);
        } catch (LogicException e) {
            page = redirectToErrorPage(requestContent,e);
        }
        return page;
    }
}
