package by.liudchyk.audiotracks.logic;

import by.liudchyk.audiotracks.dao.CommentDAO;
import by.liudchyk.audiotracks.database.ConnectionPool;
import by.liudchyk.audiotracks.database.ProxyConnection;
import by.liudchyk.audiotracks.validator.Validator;

import java.util.Date;

/**
 * Class {@code CommentLogic} is a service class used to connect commands
 * with CommentDAO.
 *
 * @author Liudchyk Valeriya
 * @see Validator
 */
public class CommentLogic {
    private final String ERROR_ADD_MESSAGE = "message.error.comment.add";

    /**
     * Validates all params. Transfers to CommentDAO or
     * returns wrong message.
     *
     * @param date    is date of the comment
     * @param text    is comment's text
     * @param userId  is id of comment's author
     * @param trackId is id of comment's track
     * @return empty string if adding was successfully,
     * string with message otherwise
     */
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

    /**
     * Transfer to CommentDAO to delete comment
     *
     * @param date    is date of the comment
     * @param userId  is id of comment's author
     * @param trackId is id of comment's track
     * @return true if track was deleted, false - otherwise
     */
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
