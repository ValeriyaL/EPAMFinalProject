package by.liudchyk.audiotracks.database;

/**
 * The {@code ConnectionPool} class represents an ability to connect with
 * database.
 *
 * @author Liudchyk Valeriya
 * @version 1.0
 * @see by.liudchyk.audiotracks.database.ProxyConnection
 */

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

public class ConnectionPool {
    private static final Logger LOG = LogManager.getLogger();
    private static ConnectionPool pool;
    private static AtomicBoolean instanceCreated = new AtomicBoolean(false);
    private static ReentrantLock lock = new ReentrantLock();
    private BlockingQueue<ProxyConnection> connectionQueue;
    private static InitDB init;

    /**
     * Creates an entity of {@code ConnectionPool}.
     * Fills the BlockingQueue with connections.
     */
    private ConnectionPool() {
        init = new InitDB();
        this.connectionQueue = new ArrayBlockingQueue<>(init.POOL_SIZE);
        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
        } catch (SQLException e) {
            LOG.fatal(e);
            throw new RuntimeException(e);
        }
        int size = init.POOL_SIZE;
        for (int i = 0; i < size; i++) {
            addConnection();
        }
        checkNumberOfConnections();
    }

    /**
     * Adds ProxyConnection to the BlockingQueue of connections.
     */
    private void addConnection() {
        try {
            Connection connection = DriverManager.getConnection(init.DATABASE, init.DB_LOGIN, init.DB_PASSWORD);
            ProxyConnection pc = new ProxyConnection(connection);
            this.connectionQueue.put(pc);
        } catch (SQLException | InterruptedException e) {
            LOG.error("Connection wasn't add into connection queue", e);
        }
    }

    /**
     * Checks number of connections in queue and tries to refill the pool.
     */
    private void checkNumberOfConnections() {
        if (connectionQueue.size() != init.POOL_SIZE) {
            int number = init.POOL_SIZE - connectionQueue.size();
            LOG.warn("Trying to recreate " + number + " connections");
            for (int i = 0; i < number; i++) {
                addConnection();
            }
        }
        if (connectionQueue.size() == 0) {
            LOG.fatal("Can't add any connection into pull");
            throw new RuntimeException("Can't add any connection into pull");
        }
    }

    /**
     * Creates connection pool or take it if it is already created.
     * @return ConnectionPool object.
     */
    public static ConnectionPool getInstance() {
        if (!instanceCreated.get()) {
            lock.lock();
            try {
                if (pool == null) {
                    pool = new ConnectionPool();
                    instanceCreated.getAndSet(true);
                }
            } finally {
                lock.unlock();
            }
        }
        return pool;
    }

    /**
     * Takes ProxyConnection from the pool queue
     * @return ProxyConnection object
     */
    public ProxyConnection takeConnection() {
        ProxyConnection connection = null;
        try {
            connection = connectionQueue.take();
        } catch (InterruptedException e) {
            LOG.error(e);
        }
        return connection;
    }

    /**
     * Returns ProxyConnection to the pool queue
     * @param connection is ProxyConnection object
     */
    void closeConnection(ProxyConnection connection) {
        try {
            connectionQueue.put(connection);
        } catch (InterruptedException e) {
            LOG.error(e);
        }
    }

    /**
     * Closes pool by closing all connections in a pool
     */
    public void closePool() {
        int size = Integer.valueOf(init.POOL_SIZE);
        try {
            for (int i = 0; i < size; i++) {
                connectionQueue.take().realClose();
            }
        } catch (SQLException | InterruptedException e) {
            LOG.error(e);
        }
    }
}