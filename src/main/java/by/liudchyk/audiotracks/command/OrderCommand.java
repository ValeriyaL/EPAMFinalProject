package by.liudchyk.audiotracks.command;

import by.liudchyk.audiotracks.entity.Track;
import by.liudchyk.audiotracks.entity.User;
import by.liudchyk.audiotracks.exception.LogicException;
import by.liudchyk.audiotracks.logic.OrderLogic;
import by.liudchyk.audiotracks.logic.TrackLogic;
import by.liudchyk.audiotracks.manager.ConfigurationManager;
import by.liudchyk.audiotracks.servlet.SessionRequestContent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Class {@code OrderCommand} is used to represent tracks in some order.
 * Is an abstract class for all classes the redirect on pages with tracks
 * in order
 *
 * @author LiudchykValeriya
 * @see ActionCommand
 */
public abstract class OrderCommand extends ActionCommand {
    public final String TRACKS_ATTRIBUTE = "allTracks";
    final String TRACKS_AZ_PATH = "path.page.tracksAZ";
    public final int TRACKS_ON_PAGE = 5;
    final String IS_PAGINATION = "isPagination";
    public final String NUM_PAGE_ATTRIBUTE = "pageNumber";
    public final int FIRST_PAGE = 1;
    final String TRACKS_ON_PAGES_ATTR = "tracksPaged";
    public final String NUMBER_OF_PAGES_ATTR = "numOfPages";
    final String COMM_PARAMETER = "comm";
    final String ORDERS_PATH = "path.page.orders";

    /**
     * Represents tracks for the first page
     *
     * @param requestContent is content of requests
     * @param tracks         is ArrayList of tracks
     * @param all            all tracks by pages
     * @return ArrayList of tracks for the first page
     */
    public ArrayList<Track> paginationTracks(SessionRequestContent requestContent, ArrayList<Track> tracks, Map<Integer, ArrayList<Track>> all) {
        HashMap<Integer, ArrayList<Track>> tracksMap;
        TrackLogic trackLogic = new TrackLogic();
        if (!all.isEmpty()) {
            tracksMap = (HashMap<Integer, ArrayList<Track>>) trackLogic.divideIntoPages(TRACKS_ON_PAGE, tracks);
            tracks = tracksMap.get(FIRST_PAGE);
            requestContent.setSessionAttribute(IS_PAGINATION, TRUE);
            requestContent.setSessionAttribute(TRACKS_ON_PAGES_ATTR, tracksMap);
            requestContent.setSessionAttribute(NUMBER_OF_PAGES_ATTR, tracksMap.size());
        } else {
            requestContent.setSessionAttribute(IS_PAGINATION, FALSE);
            requestContent.setSessionAttribute(TRACKS_ON_PAGES_ATTR, null);
            requestContent.setSessionAttribute(NUMBER_OF_PAGES_ATTR, 0);
        }
        return tracks;
    }

    /**
     * Redirect to user's tracks page and divides it into pages
     *
     * @param requestContent is content of the request
     * @return path to tracks page
     */
    public String userTracks(SessionRequestContent requestContent) {
        String page;
        ArrayList<Track> tracks;
        TrackLogic trackLogic = new TrackLogic();
        OrderLogic orderLogic = new OrderLogic();
        try {
            User user = (User) requestContent.getSessionAttribute(USER_ATTRIBUTE);
            tracks = orderLogic.findAllUserOrders(user);
            Map<Integer, ArrayList<Track>> all = trackLogic.divideIntoPages(TRACKS_ON_PAGE, tracks);
            requestContent.setSessionAttribute(NUMBER_OF_PAGES_ATTR, FIRST_PAGE);
            tracks = paginationTracks(requestContent, tracks, all);
            requestContent.setSessionAttribute(NUM_PAGE_ATTRIBUTE, Integer.toString(FIRST_PAGE));
            requestContent.setAttribute(TRACKS_ATTRIBUTE, tracks);
            requestContent.setSessionAttribute(TRACKS_ATTRIBUTE, tracks);
            page = ConfigurationManager.getProperty(ORDERS_PATH);
        } catch (LogicException e) {
            page = redirectToErrorPage(requestContent, e);
        }
        return page;
    }

}
