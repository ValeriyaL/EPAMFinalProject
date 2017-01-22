package by.liudchyk.audiotracks.command;

import by.liudchyk.audiotracks.exception.LogicException;
import by.liudchyk.audiotracks.servlet.SessionRequestContent;

/**
 * Class {@code TrackInfoCommand} is used to represent all
 * info about track
 *
 * @author LiudchykValeriya
 * @see ActionCommand
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
