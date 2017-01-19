package by.liudchyk.audiotracks.command;

import by.liudchyk.audiotracks.entity.Comment;
import by.liudchyk.audiotracks.entity.Track;
import by.liudchyk.audiotracks.exception.LogicException;
import by.liudchyk.audiotracks.logic.TrackLogic;
import by.liudchyk.audiotracks.manager.ConfigurationManager;
import by.liudchyk.audiotracks.servlet.SessionRequestContent;

import java.util.ArrayList;

/**
 * Created by Admin on 02.01.2017.
 */
public class TrackInfoCommand extends ActionCommand {
    private final String TRACK_ID_PARAMETER = "track";
    private final String TRACK_ATTRIBUTE = "trackInfo";
    private final String COMMENTS_ATTRIBUTE = "comments";
    private final String PAGE_TRACK_PATH = "path.page.track";

    @Override
    public String execute(SessionRequestContent requestContent) {
        String page;
        int trackId = Integer.valueOf(requestContent.getParameter(TRACK_ID_PARAMETER));
        TrackLogic trackLogic = new TrackLogic();
        try {
            ArrayList<Comment> comments = trackLogic.findAllCommentsById(trackId);
            Track track = trackLogic.findTrackById(trackId);
            requestContent.setSessionAttribute(TRACK_ATTRIBUTE, track);
            requestContent.setSessionAttribute(COMMENTS_ATTRIBUTE, comments);
            page = ConfigurationManager.getProperty(PAGE_TRACK_PATH);
        }catch (LogicException e){
            page = redirectToErrorPage(requestContent,e);
        }
        return page;
    }
}
