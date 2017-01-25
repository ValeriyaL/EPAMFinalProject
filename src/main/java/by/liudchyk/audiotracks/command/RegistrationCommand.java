package by.liudchyk.audiotracks.command;

import by.liudchyk.audiotracks.exception.LogicException;
import by.liudchyk.audiotracks.logic.UserLogic;
import by.liudchyk.audiotracks.manager.ConfigurationManager;
import by.liudchyk.audiotracks.manager.MessageManager;
import by.liudchyk.audiotracks.servlet.SessionRequestContent;

/**
 * Class {@code RegistrationCommand} is used to register
 * new user in a system
 *
 * @author LiudchykValeriya
 * @see ActionCommand
 */
public class RegistrationCommand extends ActionCommand {
    private final String LOGIN_PATH = "path.page.login";
    private final String NAME_PARAM = "nickname";
    private final String PASSWORD_PARAM = "password";
    private final String EMAIL_PARAM = "email";
    private final String CONF_PASS_PARAM = "confirmPassword";
    private final String CARD_PARAM = "card";
    private final String MESSAGE = "message.success.register";


    @Override
    public String execute(SessionRequestContent requestContent) {
        String page;
        UserLogic userLogic = new UserLogic();
        String name = requestContent.getParameter(NAME_PARAM);
        String password = requestContent.getParameter(PASSWORD_PARAM);
        String email = requestContent.getParameter(EMAIL_PARAM);
        String confirmPassword = requestContent.getParameter(CONF_PASS_PARAM);
        String card = requestContent.getParameter(CARD_PARAM);
        try {
            String msgPath = userLogic.registerUser(name, email, password, confirmPassword, card);
            if (MESSAGE.equals(msgPath)) {
                String message = messageManager.getProperty(msgPath, (String) requestContent.getSessionAttribute(PARAMETER));
                requestContent.setAttribute(SUCCESS_ATTRIBUTE, message);
                page = ConfigurationManager.getProperty(LOGIN_PATH);
            } else {
                requestContent.setAttribute(NAME_PARAM, name);
                requestContent.setAttribute(PASSWORD_PARAM, password);
                requestContent.setAttribute(EMAIL_PARAM, email);
                requestContent.setAttribute(CONF_PASS_PARAM, confirmPassword);
                requestContent.setAttribute(CARD_PARAM, card);
                String message = messageManager.getProperty(msgPath, (String) requestContent.getSessionAttribute(PARAMETER));
                requestContent.setAttribute(MISTAKE_ATTRIBUTE, message);
                page = ConfigurationManager.getProperty((String) requestContent.getSessionAttribute(PATH_ATTRIBUTE));
            }
        } catch (LogicException e) {
            page = redirectToErrorPage(requestContent, e);
        }
        return page;
    }
}
