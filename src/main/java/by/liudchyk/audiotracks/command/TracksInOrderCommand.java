package by.liudchyk.audiotracks.command;

import by.liudchyk.audiotracks.entity.Track;
import by.liudchyk.audiotracks.exception.LogicException;
import by.liudchyk.audiotracks.logic.TrackLogic;
import by.liudchyk.audiotracks.manager.ConfigurationManager;
import by.liudchyk.audiotracks.servlet.SessionRequestContent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Admin on 30.12.2016.
 */
public class TracksInOrderCommand extends OrderCommand {

    @Override
    public String execute(SessionRequestContent requestContent) {
        String page;
        ArrayList<Track> tracks;
        TrackLogic trackLogic = new TrackLogic();
        try {
            String order = requestContent.getParameter(COMM_PARAMETER);
            tracks = trackLogic.findAllTracksInOrder(order);
            Map<Integer, ArrayList<Track>> all = trackLogic.divideIntoPages(TRACKS_ON_PAGE, tracks);
            requestContent.setSessionAttribute(NUMBER_OF_PAGES_ATTR, FIRST_PAGE);
            tracks = paginationTracks(requestContent, tracks, trackLogic, all);
            requestContent.setSessionAttribute(COMM_PARAMETER, order);
            requestContent.setSessionAttribute(NUM_PAGE_ATTRIBUTE, Integer.toString(FIRST_PAGE));
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
