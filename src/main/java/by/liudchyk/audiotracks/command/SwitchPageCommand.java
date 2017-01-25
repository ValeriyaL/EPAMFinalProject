package by.liudchyk.audiotracks.command;

import by.liudchyk.audiotracks.entity.Track;
import by.liudchyk.audiotracks.manager.ConfigurationManager;
import by.liudchyk.audiotracks.servlet.SessionRequestContent;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class {@code SwitchPageCommand} is used to find tracks by page
 *
 * @author LiudchykValeriya
 * @see ActionCommand
 */
public class SwitchPageCommand extends ActionCommand {
    private final String NUM_PAGE_ATTRIBUTE = "pageNumber";
    private final String TRACKS_ON_PAGES_ATTR = "tracksPaged";
    private final String TRACKS_ATTRIBUTE = "allTracks";

    @Override
    public String execute(SessionRequestContent requestContent) {
        String page;
        try {
            HashMap<Integer, ArrayList<Track>> tracksPaged = (HashMap<Integer, ArrayList<Track>>) requestContent.getSessionAttribute(TRACKS_ON_PAGES_ATTR);
            Integer pageNumber = Integer.valueOf(requestContent.getParameter(NUM_PAGE_ATTRIBUTE));
            ArrayList<Track> tracks = tracksPaged.get(pageNumber);
            requestContent.setAttribute(TRACKS_ATTRIBUTE, tracks);
            requestContent.setSessionAttribute(NUM_PAGE_ATTRIBUTE, pageNumber);
            requestContent.setSessionAttribute(TRACKS_ATTRIBUTE, tracks);
            page = ConfigurationManager.getProperty((String) requestContent.getSessionAttribute(PATH_ATTRIBUTE));
        } catch (NumberFormatException e) {
            LOG.error(e);
            page = redirectToErrorPageWithMessage(requestContent, "Wrong format in page number parameter");
        }
        return page;
    }
}
