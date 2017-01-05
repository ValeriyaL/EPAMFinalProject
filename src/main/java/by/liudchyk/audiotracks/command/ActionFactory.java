package by.liudchyk.audiotracks.command;

import by.liudchyk.audiotracks.servlet.SessionRequestContent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ActionFactory {
    private static final Logger LOG = LogManager.getLogger();
    private static final String COMMAND_PARAMETER = "command";

    public ActionCommand defineCommand(SessionRequestContent sessionRequestContent) {
        ActionCommand current = new EmptyCommand();
        String actionS = sessionRequestContent.getParameter(COMMAND_PARAMETER);
        if (actionS == null || actionS.isEmpty()) {
            return current;
        }
        try {
            CommandType currentEnum = CommandType.valueOf(actionS.toUpperCase());
            current = currentEnum.getCurrentCommand();
        } catch (IllegalArgumentException e) {
            LOG.error("No such command",e);
        }
        return current;
    }
}

