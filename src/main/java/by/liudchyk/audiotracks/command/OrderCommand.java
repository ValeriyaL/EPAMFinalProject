package by.liudchyk.audiotracks.command;

import by.liudchyk.audiotracks.entity.Track;
import by.liudchyk.audiotracks.logic.TrackLogic;
import by.liudchyk.audiotracks.servlet.SessionRequestContent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Admin on 02.01.2017.
 */
public abstract class OrderCommand extends ActionCommand{
    final String TRACKS_ATTRIBUTE = "allTracks";
    final String TRACKS_AZ_PATH = "path.page.tracksAZ";
    final int TRACKS_ON_PAGE = 5;
    final String IS_PAGINATION = "isPagination";
    final String NUM_PAGE_ATTRIBUTE = "pageNumber";
    final int FIRST_PAGE = 1;
    final String TRACKS_ON_PAGES_ATTR = "tracksPaged";
    final String NUMBER_OF_PAGES_ATTR = "numOfPages";
    final String COMM_PARAMETER = "comm";
    final String TRUE = "true";
    final String FALSE = "false";

    ArrayList<Track> paginationTracks(SessionRequestContent requestContent, ArrayList<Track> tracks, TrackLogic trackLogic, Map<Integer, ArrayList<Track>> all) {
        HashMap<Integer, ArrayList<Track>> tracksMap;
        if (!all.isEmpty()) {
            tracksMap = (HashMap<Integer, ArrayList<Track>>) trackLogic.divideIntoPages(TRACKS_ON_PAGE, tracks);
            tracks = tracksMap.get(FIRST_PAGE);
            requestContent.setSessionAttribute(IS_PAGINATION, TRUE);
            requestContent.setSessionAttribute(TRACKS_ON_PAGES_ATTR, tracksMap);
            requestContent.setSessionAttribute(NUMBER_OF_PAGES_ATTR, tracksMap.size());
        }else{
            requestContent.setSessionAttribute(IS_PAGINATION, FALSE);
            requestContent.setSessionAttribute(TRACKS_ON_PAGES_ATTR, null);
            requestContent.setSessionAttribute(NUMBER_OF_PAGES_ATTR, 0);
        }
        return tracks;
    }
}
