package by.liudchyk.audiotracks.command.client;

import by.liudchyk.audiotracks.command.OrderCommand;
import by.liudchyk.audiotracks.servlet.SessionRequestContent;

/**
 * Created by Admin on 04.01.2017.
 */
public class OrdersCommand extends OrderCommand {

    @Override
    public String execute(SessionRequestContent requestContent) {
        return userTracks(requestContent);
    }
}
