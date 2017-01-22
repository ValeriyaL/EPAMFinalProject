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
 * Class {@code GenreOrderCommand} is used to find tracks
 * with genre
 *
 * @author LiudchykValeriya
 * @see OrderCommand
 */
public class GenreOrderCommand extends OrderCommand {
    private final String GENRE_PARAMETER = "genre";

    @Override
    public String execute(SessionRequestContent requestContent) {
        String page;
        ArrayList<Track> tracks;
        TrackLogic trackLogic = new TrackLogic();
        try {
            String genre = requestContent.getParameter(GENRE_PARAMETER);
            tracks = trackLogic.findTracksByGenre(genre);
            Map<Integer, ArrayList<Track>> all = trackLogic.divideIntoPages(TRACKS_ON_PAGE, tracks);
            requestContent.setSessionAttribute(NUMBER_OF_PAGES_ATTR, FIRST_PAGE);
            tracks = paginationTracks(requestContent, tracks, all);
            requestContent.setSessionAttribute(COMM_PARAMETER, genre);
            requestContent.setSessionAttribute(NUM_PAGE_ATTRIBUTE, Integer.toString(FIRST_PAGE));
            requestContent.setAttribute(TRACKS_ATTRIBUTE, tracks);
            requestContent.setSessionAttribute(TRACKS_ATTRIBUTE, tracks);
            page = ConfigurationManager.getProperty(TRACKS_AZ_PATH);
        } catch (LogicException e) {
            page = redirectToErrorPage(requestContent,e);
        }
        return page;
    }


}
