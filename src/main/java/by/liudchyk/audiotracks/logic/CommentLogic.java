package by.liudchyk.audiotracks.logic;

import by.liudchyk.audiotracks.dao.CommentDAO;
import by.liudchyk.audiotracks.database.ConnectionPool;
import by.liudchyk.audiotracks.database.ProxyConnection;
import by.liudchyk.audiotracks.validator.Validator;

import java.util.Date;

/**
 * Created by Admin on 03.01.2017.
 */
public class CommentLogic {
    private final String ERROR_ADD_MESSAGE = "message.error.comment.add";

    public String addComment(Date date, String text, int userId, int trackId) {
        Validator validator = new Validator();
        String msg = validator.isCommentValid(text);
        if (!msg.isEmpty()) {
            return msg;
        }
        ProxyConnection connection = ConnectionPool.getInstance().takeConnection();
        CommentDAO commentDAO = new CommentDAO(connection);
        try {
            return commentDAO.addComment(date, text, userId, trackId) ? "" : ERROR_ADD_MESSAGE;
        } finally {
            commentDAO.closeConnection(connection);
        }
    }

    public boolean deleteComment(int userId, int trackId, String date) {
        ProxyConnection connection = ConnectionPool.getInstance().takeConnection();
        CommentDAO commentDAO = new CommentDAO(connection);
        try {
            return commentDAO.deleteComment(userId, trackId, date);
        } finally {
            commentDAO.closeConnection(connection);
        }
    }
}
