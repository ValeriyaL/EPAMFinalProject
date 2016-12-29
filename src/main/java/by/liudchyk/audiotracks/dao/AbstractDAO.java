package by.liudchyk.audiotracks.dao;

/**
 * Created by Admin on 24.12.2016.
 */

import by.liudchyk.audiotracks.database.ProxyConnection;
import by.liudchyk.audiotracks.entity.Entity;
import by.liudchyk.audiotracks.exception.DAOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public abstract class AbstractDAO<T extends Entity> {
    protected ProxyConnection connection;
    private static final Logger LOG = LogManager.getLogger();

    public AbstractDAO(ProxyConnection connection) {
        this.connection = connection;
    }

    public abstract List<T> findAll() throws DAOException;

    public void closeStatement(Statement statement) {
        try {
            if (statement != null) {
                statement.close();
            } else {
                LOG.warn("Statement was null");
            }
        } catch (SQLException e) {
            LOG.warn("Mistake in statement closing", e);
        }
    }
}
