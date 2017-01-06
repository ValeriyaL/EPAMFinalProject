package by.liudchyk.audiotracks.command;

import by.liudchyk.audiotracks.exception.LogicException;
import by.liudchyk.audiotracks.logic.TrackLogic;
import by.liudchyk.audiotracks.manager.ConfigurationManager;
import by.liudchyk.audiotracks.manager.MessageManager;
import by.liudchyk.audiotracks.servlet.SessionRequestContent;

/**
 * Created by Admin on 06.01.2017.
 */
public class AddTrackCommand extends ActionCommand {
    private final String TITLE_PARAM = "title";
    private final String ARTIST_PARAM = "artist";
    private final String GENRE_PARAM = "genre";
    private final String PRICE_PARAM = "price";
    private final String LENGTH_PARAM = "length";
    private final String LINK_PARAM = "link";
    private final String MESSAGE = "message.success.add";
    private final String MESSAGE_PATH = "path.page.message";

    @Override
    public String execute(SessionRequestContent requestContent) {
        String page;
        TrackLogic trackLogic = new TrackLogic();
        String title = requestContent.getParameter(TITLE_PARAM);
        String artist = requestContent.getParameter(ARTIST_PARAM);
        String genre = requestContent.getParameter(GENRE_PARAM);
        String price = requestContent.getParameter(PRICE_PARAM);
        String link = requestContent.getParameter(LINK_PARAM);
        String length  = requestContent.getParameter(LENGTH_PARAM);
        try {
            String msgPath = trackLogic.addTrack(title,artist,genre,price,link,length);
            if (MESSAGE.equals(msgPath)) {
                String message = MessageManager.getProperty(msgPath, (String) requestContent.getSessionAttribute(PARAMETER));
                requestContent.setAttribute(SUCCESS_ATTRIBUTE, message);
                page = ConfigurationManager.getProperty(MESSAGE_PATH);
            } else {
                requestContent.setAttribute(TITLE_PARAM, title);
                requestContent.setAttribute(ARTIST_PARAM, artist);
                requestContent.setAttribute(GENRE_PARAM, genre);
                requestContent.setAttribute(PRICE_PARAM, price);
                requestContent.setAttribute(LINK_PARAM, link);
                requestContent.setAttribute(LENGTH_PARAM, length);
                String message = MessageManager.getProperty(msgPath, (String) requestContent.getSessionAttribute(PARAMETER));
                requestContent.setAttribute(MISTAKE_ATTRIBUTE, message);
                page = ConfigurationManager.getProperty((String) requestContent.getSessionAttribute(PATH_ATTRIBUTE));
            }
        } catch (LogicException e) {
            LOG.error(e);
            requestContent.setAttribute(ERROR_MSG_ATTRIBUTE, e.getMessage());
            page = ConfigurationManager.getProperty(ERROR_PATH);
        }
        return page;
    }
}
