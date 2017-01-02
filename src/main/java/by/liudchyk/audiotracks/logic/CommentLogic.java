package by.liudchyk.audiotracks.logic;

import by.liudchyk.audiotracks.dao.CommentDAO;
import by.liudchyk.audiotracks.dao.UserDAO;
import by.liudchyk.audiotracks.database.ConnectionPool;
import by.liudchyk.audiotracks.database.ProxyConnection;
import by.liudchyk.audiotracks.exception.DAOException;
import by.liudchyk.audiotracks.exception.LogicException;
import by.liudchyk.audiotracks.validator.Validator;

import java.util.Date;

/**
 * Created by Admin on 03.01.2017.
 */
public class CommentLogic {
    private final String ERROR_ADD_MESSAGE = "message.error.comment.add";

    public String addComment(Date date,String text,int userId,int trackId) throws LogicException{
        Validator validator = new Validator();
        String msg = validator.isCommentValid(text);
        if (!msg.isEmpty()) {
            return msg;
        }
        ProxyConnection connection = ConnectionPool.getInstance().getConnection();
        CommentDAO commentDAO = new CommentDAO(connection);
        try {
            if (commentDAO.addComment(date,text,userId,trackId)) {
                return "";
            } else {
                return ERROR_ADD_MESSAGE;
            }
        } catch (DAOException e) {
            throw new LogicException("Can't change user money", e);
        } finally {
           commentDAO.closeConnection(connection);
        }
    }
}
