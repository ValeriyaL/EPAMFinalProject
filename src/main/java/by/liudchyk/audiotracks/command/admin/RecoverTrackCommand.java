package by.liudchyk.audiotracks.command.admin;

import by.liudchyk.audiotracks.command.ActionCommand;
import by.liudchyk.audiotracks.exception.LogicException;
import by.liudchyk.audiotracks.logic.TrackLogic;
import by.liudchyk.audiotracks.manager.ConfigurationManager;
import by.liudchyk.audiotracks.manager.MessageManager;
import by.liudchyk.audiotracks.servlet.SessionRequestContent;

/**
 * Class {@code RecoverTrackCommand} is used to represent
 * all recovered tracks
 *
 * @author LiudchykValeriya
 * @see ActionCommand
 */
public class RecoverTrackCommand extends ActionCommand {
    private final String TRACK_ID_PARAMETER = "track";
    private final String MESSAGE_PATH = "path.page.message";
    private final String MESSAGE = "message.success.track.recover";

    @Override
    public String execute(SessionRequestContent requestContent) {
        String page;
        try {
            String role = (String) requestContent.getSessionAttribute(ROLE_ATTRIBUTE);
            if (ADMIN.equals(role)) {
                int trackId = Integer.valueOf(requestContent.getParameter(TRACK_ID_PARAMETER));
                TrackLogic trackLogic = new TrackLogic();
                try {
                    trackLogic.recoverTrackById(trackId);
                    String message = messageManager.getProperty(MESSAGE, (String) requestContent.getSessionAttribute(PARAMETER));
                    requestContent.setAttribute(SUCCESS_ATTRIBUTE, message);
                    page = ConfigurationManager.getProperty(MESSAGE_PATH);
                } catch (LogicException e) {
                    page = redirectToErrorPage(requestContent, e);
                }
            } else {
                page = redirectToMain(requestContent);
            }
        } catch (NumberFormatException e) {
            LOG.error("Wrong format in trackId parameter", e);
            page = redirectToErrorPageWithMessage(requestContent, "Wrong format in trackId parameter");

        }
        return page;
    }
}
