package by.liudchyk.audiotracks.command;

import by.liudchyk.audiotracks.entity.User;
import by.liudchyk.audiotracks.exception.LogicException;
import by.liudchyk.audiotracks.logic.UserLogic;
import by.liudchyk.audiotracks.manager.ConfigurationManager;
import by.liudchyk.audiotracks.manager.LanguageManager;
import by.liudchyk.audiotracks.servlet.SessionRequestContent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by Admin on 27.12.2016.
 */
public class ChangeCardCommand implements ActionCommand {
    private static final Logger LOG = LogManager.getLogger();
    private final String NAME_PARAM = "card";
    private final String USER_ATTRIBUTE = "user";
    private final String SUCCESS_MESSAGE = "message.success.change.card";
    private final String SUCCESS_ATTRIBUTE = "info";
    private final String SUCCESS_PATH = "path.page.account";
    private final String MISTAKE_ATTRIBUTE = "mistake";
    private static final String PARAMETER = "locale";
    private final String PATH_ATTRIBUTE = "page";

    @Override
    public String execute(SessionRequestContent requestContent) {
        String page = null;
        UserLogic userLogic = new UserLogic();
        String newCard = requestContent.getParameter(NAME_PARAM);
        try {
            User tempUser =(User) requestContent.getSessionAttribute(USER_ATTRIBUTE);
            String msgPath = userLogic.changeUserCard(newCard, tempUser.getId());
            if(SUCCESS_MESSAGE.equals(msgPath)){
                tempUser.setCardNumber(newCard);
                requestContent.setSessionAttribute(USER_ATTRIBUTE, tempUser);
                String message = LanguageManager.getProperty(SUCCESS_MESSAGE,(String) requestContent.getSessionAttribute(PARAMETER));
                requestContent.setAttribute(SUCCESS_ATTRIBUTE, message);
                page = ConfigurationManager.getProperty(SUCCESS_PATH);
            }else{
                requestContent.setAttribute(NAME_PARAM,newCard);
                String message = LanguageManager.getProperty(msgPath,(String) requestContent.getSessionAttribute(PARAMETER));
                requestContent.setAttribute(MISTAKE_ATTRIBUTE, message);
                page = ConfigurationManager.getProperty((String) requestContent.getSessionAttribute(PATH_ATTRIBUTE));

            }
        } catch (LogicException e) {
            LOG.error(e);
            // TODO перенаправление
        }
        return page;
    }
}
