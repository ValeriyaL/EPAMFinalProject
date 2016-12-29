package by.liudchyk.audiotracks.command;

import by.liudchyk.audiotracks.entity.Track;
import by.liudchyk.audiotracks.exception.LogicException;
import by.liudchyk.audiotracks.logic.TrackLogic;
import by.liudchyk.audiotracks.manager.ConfigurationManager;
import by.liudchyk.audiotracks.servlet.SessionRequestContent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

/**
 * Created by Admin on 28.12.2016.
 */
public class MainCommand extends ActionCommand {
    private static final Logger LOG = LogManager.getLogger();
    private static final String PATH_MAIN = "path.page.main";
    private final String TRACKS_ATTRIBUTE = "tracks";

    @Override
    public String execute(SessionRequestContent requestContent) {
        String page = null;
        ArrayList<Track> tracks;
        TrackLogic trackLogic = new TrackLogic();
        try {
            tracks = trackLogic.findLastOrders();
            requestContent.setAttribute(TRACKS_ATTRIBUTE, tracks);
            requestContent.setSessionAttribute(TRACKS_ATTRIBUTE, tracks);
            page = ConfigurationManager.getProperty(PATH_MAIN);
        } catch (LogicException e) {
            LOG.error(e);
            requestContent.setAttribute(ERROR_MSG_ATTRIBUTE, e.getMessage());
            page = ConfigurationManager.getProperty(ERROR_PATH);
        }
        return page;
    }
}
