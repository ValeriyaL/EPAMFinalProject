package by.liudchyk.audiotracks.command;

import by.liudchyk.audiotracks.exception.LogicException;
import by.liudchyk.audiotracks.logic.TrackLogic;
import by.liudchyk.audiotracks.manager.ConfigurationManager;
import by.liudchyk.audiotracks.manager.MessageManager;
import by.liudchyk.audiotracks.servlet.SessionRequestContent;

/**
 * Created by Admin on 05.01.2017.
 */
public class RecoverTrackCommand extends ActionCommand {
    private final String TRACK_ID_PARAMETER = "track";
    private final String MESSAGE_PATH = "path.page.message";
    private final String MESSAGE = "message.success.track.recover";

    @Override
    public String execute(SessionRequestContent requestContent) {
        String page;
        int trackId = Integer.valueOf(requestContent.getParameter(TRACK_ID_PARAMETER));
        TrackLogic trackLogic = new TrackLogic();
        try {
            trackLogic.recoverTrackById(trackId);
            String message = MessageManager.getProperty(MESSAGE, (String) requestContent.getSessionAttribute(PARAMETER));
            requestContent.setAttribute(SUCCESS_ATTRIBUTE, message);
            page = ConfigurationManager.getProperty(MESSAGE_PATH);
        }catch (LogicException e){
            page = redirectToErrorPage(requestContent,e);
        }
        return page;
    }
}
