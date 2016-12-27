package by.liudchyk.audiotracks.command;

import by.liudchyk.audiotracks.entity.User;
import by.liudchyk.audiotracks.exception.LogicException;
import by.liudchyk.audiotracks.logic.LoginLogic;
import by.liudchyk.audiotracks.logic.UserLogic;
import by.liudchyk.audiotracks.manager.ConfigurationManager;
import by.liudchyk.audiotracks.manager.LanguageManager;
import by.liudchyk.audiotracks.servlet.SessionRequestContent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by Admin on 24.12.2016.
 */
public class LoginCommand implements ActionCommand {
    private static final Logger LOG = LogManager.getLogger();
    private final String NAME_PARAM = "nickname";
    private final String PASSWORD_PARAM = "password";
    private final String PATH = "path.page.main";
    private static final String PARAMETER = "locale";
    private final String MESSAGE = "message.error.login";
    private final String MISTAKE_ATTRIBUTE = "mistake";
    private final String PATH_ATTRIBUTE = "page";
    private final String IS_LOGIN_ATTRIBUTE = "isLogin";
    private final String USER_ATTRIBUTE = "user";

    @Override
    public String execute(SessionRequestContent requestContent){
        String page = null;
        LoginLogic loginLogic = new LoginLogic();
        String name = requestContent.getParameter(NAME_PARAM);
        String password = requestContent.getParameter(PASSWORD_PARAM);
        try {
            if (loginLogic.checkLogin(name, password)) {
                page = ConfigurationManager.getProperty(PATH);
                requestContent.setSessionAttribute(IS_LOGIN_ATTRIBUTE, "true");
                UserLogic userLogic = new UserLogic();
                User user = userLogic.findUserByLogin(name);
                requestContent.setSessionAttribute(USER_ATTRIBUTE, user);
            }else{
                String message = LanguageManager.getProperty(MESSAGE,(String) requestContent.getSessionAttribute(PARAMETER));
                requestContent.setAttribute(MISTAKE_ATTRIBUTE, message);
                page = ConfigurationManager.getProperty((String) requestContent.getSessionAttribute(PATH_ATTRIBUTE));
            }
        }catch (LogicException e){
            LOG.error(e);
            //перенаправление на ошибочную страницу (ту же самую?)
        }
        return page;
    }
}
