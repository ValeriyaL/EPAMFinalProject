package by.liudchyk.audiotracks.command;

import by.liudchyk.audiotracks.manager.ConfigurationManager;
import by.liudchyk.audiotracks.servlet.SessionRequestContent;

/**
 * Created by Admin on 23.12.2016.
 */
public class EmptyCommand extends ActionCommand {
    @Override
    public String execute(SessionRequestContent requestContent) {
        return ConfigurationManager.getProperty((String) requestContent.getSessionAttribute(PATH_ATTRIBUTE));
    }
}
