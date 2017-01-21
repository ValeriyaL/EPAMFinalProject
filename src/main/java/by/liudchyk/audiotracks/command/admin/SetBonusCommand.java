package by.liudchyk.audiotracks.command.admin;

import by.liudchyk.audiotracks.command.ActionCommand;
import by.liudchyk.audiotracks.entity.User;
import by.liudchyk.audiotracks.exception.LogicException;
import by.liudchyk.audiotracks.logic.UserLogic;
import by.liudchyk.audiotracks.manager.ConfigurationManager;
import by.liudchyk.audiotracks.manager.MessageManager;
import by.liudchyk.audiotracks.servlet.SessionRequestContent;

/**
 * Created by Admin on 07.01.2017.
 */
public class SetBonusCommand extends ActionCommand {
    private final String NICKNAME_PARAMETER = "userNickname";
    private final String BONUS_ATTR = "bonus";
    private final String MESSAGE = "message.success.change.bonus";
    private final String ERROR_MSG = "message.error.change.bonus";

    @Override
    public String execute(SessionRequestContent requestContent) {
        String page;
        String nickname = requestContent.getParameter(NICKNAME_PARAMETER);
        String bonus = requestContent.getParameter(BONUS_ATTR);
        UserLogic userLogic = new UserLogic();
        try{
            int newBonus = userLogic.changeBonusByNickname(nickname, bonus);
            if(newBonus!=-1) {
                requestContent.setSessionAttribute(BONUS_ATTR, newBonus);
                String message = MessageManager.getProperty(MESSAGE, (String) requestContent.getSessionAttribute(PARAMETER));
                requestContent.setAttribute(SUCCESS_ATTRIBUTE, message);
                page = ConfigurationManager.getProperty((String) requestContent.getSessionAttribute(PATH_ATTRIBUTE));
                User admin = (User)requestContent.getSessionAttribute(USER_ATTRIBUTE);
                if(nickname.equals(admin.getNickname())){
                    admin.setBonus(newBonus);
                    requestContent.setSessionAttribute(USER_ATTRIBUTE,admin);
                }
            } else {
                String message = MessageManager.getProperty(ERROR_MSG, (String) requestContent.getSessionAttribute(PARAMETER));
                requestContent.setAttribute(MISTAKE_ATTRIBUTE, message);
                page = ConfigurationManager.getProperty((String) requestContent.getSessionAttribute(PATH_ATTRIBUTE));
            }
        }catch (LogicException e){
            page = redirectToErrorPage(requestContent,e);
        }
        return page;
    }
}
