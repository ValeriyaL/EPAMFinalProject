package by.liudchyk.audiotracks.command;

import by.liudchyk.audiotracks.manager.ConfigurationManager;
import by.liudchyk.audiotracks.servlet.SessionRequestContent;

/**
 * Class {@code EmptyCommand} is used to redirect to page
 * when command doesn't exists
 *
 * @author LiudchykValeriya
 * @see ActionCommand
 */
public class EmptyCommand extends ActionCommand {
    @Override
    public String execute(SessionRequestContent requestContent) {
        return ConfigurationManager.getProperty((String) requestContent.getSessionAttribute(PATH_ATTRIBUTE));
    }
}
