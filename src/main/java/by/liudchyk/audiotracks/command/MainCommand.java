package by.liudchyk.audiotracks.command;

import by.liudchyk.audiotracks.servlet.SessionRequestContent;

/**
 * Class {@code MainCommand} is used to redirect to main page
 *
 * @author LiudchykValeriya
 * @see ActionCommand
 */
public class MainCommand extends ActionCommand {

    @Override
    public String execute(SessionRequestContent requestContent) {
        return redirectToMain(requestContent);
    }
}
