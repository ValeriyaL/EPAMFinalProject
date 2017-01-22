package by.liudchyk.audiotracks.command.client;

import by.liudchyk.audiotracks.command.OrderCommand;
import by.liudchyk.audiotracks.servlet.SessionRequestContent;

/**
 * Class {@code OrdersCommand} is used to represent all user's ordered tracks
 *
 * @author LiudchykValeriya
 * @see OrderCommand
 */
public class OrdersCommand extends OrderCommand {

    @Override
    public String execute(SessionRequestContent requestContent) {
        return userTracks(requestContent);
    }
}
