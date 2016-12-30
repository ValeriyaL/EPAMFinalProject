package by.liudchyk.audiotracks.command;

import by.liudchyk.audiotracks.entity.Track;
import by.liudchyk.audiotracks.exception.LogicException;
import by.liudchyk.audiotracks.logic.TrackLogic;
import by.liudchyk.audiotracks.manager.ConfigurationManager;
import by.liudchyk.audiotracks.servlet.SessionRequestContent;

import java.util.ArrayList;

/**
 * Created by Admin on 30.12.2016.
 */
public class TracksAlphabetCommand extends ActionCommand{
    private String TRACKS_ATTRIBUTE = "allTracks";
    private String TRACKS_AZ_PATH = "path.page.tracksAZ";

    @Override
    public String execute(SessionRequestContent requestContent) {
        String page = null;
        ArrayList<Track> tracks;
        TrackLogic trackLogic = new TrackLogic();
        try {
            tracks = trackLogic.findAllTracksInOrder();
            requestContent.setAttribute(TRACKS_ATTRIBUTE, tracks);
            requestContent.setSessionAttribute(TRACKS_ATTRIBUTE, tracks);
            page = ConfigurationManager.getProperty(TRACKS_AZ_PATH);
        } catch (LogicException e) {
            LOG.error(e);
            requestContent.setAttribute(ERROR_MSG_ATTRIBUTE, e.getMessage());
            page = ConfigurationManager.getProperty(ERROR_PATH);
        }
        return page;
    }
}
