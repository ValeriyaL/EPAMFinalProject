package by.liudchyk.audiotracks.command;

import by.liudchyk.audiotracks.exception.LogicException;
import by.liudchyk.audiotracks.logic.UserLogic;
import by.liudchyk.audiotracks.manager.ConfigurationManager;
import by.liudchyk.audiotracks.manager.LanguageManager;
import by.liudchyk.audiotracks.servlet.SessionRequestContent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by Admin on 25.12.2016.
 */
public class RegistrationCommand implements ActionCommand {
    private static final Logger LOG = LogManager.getLogger();
    private final String LOGIN_PATH = "path.page.login";
    private final String NAME_PARAM = "nickname";
    private final String PASSWORD_PARAM = "password";
    private final String EMAIL_PARAM = "email";
    private final String CONF_PASS_PARAM = "confirmPassword";
    private final String CARD_PARAM = "card";
    private final String SUCCESS_ATTRIBUTE = "info";
    private final String MESSAGE = "message.success.register";
    private final String ERROR_MESSAGE = "message.error.register";
    private static final String PARAMETER = "locale";
    private final String MISTAKE_ATTRIBUTE = "mistake";
    private final String PATH_ATTRIBUTE = "page";

    @Override
    public String execute(SessionRequestContent requestContent) {
        String page = null;
        UserLogic userLogic = new UserLogic();
        String name = requestContent.getParameter(NAME_PARAM);
        String password = requestContent.getParameter(PASSWORD_PARAM);
        String email = requestContent.getParameter(EMAIL_PARAM);
        String confirmPassword = requestContent.getParameter(CONF_PASS_PARAM);
        String card = requestContent.getParameter(CARD_PARAM);
        try {
            String msgPath = userLogic.registerUser(name, email, password, confirmPassword, card);
            if(MESSAGE.equals(msgPath)){
                String message = LanguageManager.getProperty(msgPath,(String) requestContent.getSessionAttribute(PARAMETER));
                requestContent.setAttribute(SUCCESS_ATTRIBUTE, message);
                page = ConfigurationManager.getProperty(LOGIN_PATH);
            }else{
                String message = LanguageManager.getProperty(msgPath,(String) requestContent.getSessionAttribute(PARAMETER));
                requestContent.setAttribute(MISTAKE_ATTRIBUTE, message);
                page = ConfigurationManager.getProperty((String) requestContent.getSessionAttribute(PATH_ATTRIBUTE));
            }
        }catch (LogicException e){
            LOG.error(e);
            //TODO перенаправление на стр.ошибки со своим msg
        }
        return page;
    }
}
