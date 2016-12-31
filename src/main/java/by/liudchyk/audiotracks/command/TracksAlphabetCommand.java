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
public class TracksAlphabetCommand extends ActionCommand{
    private final String TRACKS_ATTRIBUTE = "allTracks";
    private final String TRACKS_AZ_PATH = "path.page.tracksAZ";
    private final int TRACKS_ON_PAGE = 5;
    private final String IS_PAGINATION = "isPagination";
    private final String NUM_PAGE_ATTRIBUTE = "pageNumber";
    private final int FIRST_PAGE = 1;
    private final String TRACKS_ON_PAGES_ATTR = "tracksPaged";
    private final String NUMBER_OF_PAGES_ATTR = "numOfPages";

    @Override
    public String execute(SessionRequestContent requestContent) {
        String page = null;
        ArrayList<Track> tracks;
        TrackLogic trackLogic = new TrackLogic();
        try {
            tracks = trackLogic.findAllTracksInOrder();
            Map<Integer, ArrayList<Track>> all = trackLogic.divideIntoPages(TRACKS_ON_PAGE, tracks);
            HashMap<Integer,ArrayList<Track>> tracksMap;
            requestContent.setSessionAttribute(NUMBER_OF_PAGES_ATTR, FIRST_PAGE);
            if(!all.isEmpty()){
                tracksMap =(HashMap<Integer, ArrayList<Track>>) trackLogic.divideIntoPages(TRACKS_ON_PAGE, tracks);
                tracks = tracksMap.get(FIRST_PAGE);
                requestContent.setSessionAttribute(IS_PAGINATION,"true");
                requestContent.setSessionAttribute(TRACKS_ON_PAGES_ATTR, tracksMap);
                requestContent.setSessionAttribute(NUMBER_OF_PAGES_ATTR, tracksMap.size());
            }
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
