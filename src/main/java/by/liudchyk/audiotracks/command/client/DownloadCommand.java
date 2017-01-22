package by.liudchyk.audiotracks.command.client;

import by.liudchyk.audiotracks.command.ActionCommand;
import by.liudchyk.audiotracks.exception.LogicException;
import by.liudchyk.audiotracks.logic.TrackLogic;
import by.liudchyk.audiotracks.servlet.SessionRequestContent;

/**
 * Class {@code DownloadCommand} is used to download track
 *
 * @author LiudchykValeriya
 * @see ActionCommand
 */
public class DownloadCommand extends ActionCommand {
    private final String TRACK_ATTRIBUTE = "track";

    @Override
    public String execute(SessionRequestContent requestContent) {
        String filePath;
        int id = Integer.valueOf(requestContent.getParameter(TRACK_ATTRIBUTE));
        TrackLogic trackLogic = new TrackLogic();
        try {
            filePath = trackLogic.findPathByTrackId(id);
        } catch (LogicException e) {
            LOG.error(e);
            filePath = "";
        }
        return filePath;
    }
}
