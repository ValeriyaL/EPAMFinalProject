package by.liudchyk.audiotracks.command;

import by.liudchyk.audiotracks.servlet.SessionRequestContent;

public interface ActionCommand {
    String execute(SessionRequestContent requestContent);
}
