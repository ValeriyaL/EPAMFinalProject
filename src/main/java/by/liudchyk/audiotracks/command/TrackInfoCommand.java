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

    @Override
    public String execute(SessionRequestContent requestContent) {
        String page;
        int trackId = Integer.valueOf(requestContent.getParameter(TRACK_ID_PARAMETER));
        try {
            page = redirectToTrackPage(trackId, requestContent);
        } catch (LogicException e) {
            page = redirectToErrorPage(requestContent, e);
        }
        return page;
    }
}
