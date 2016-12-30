package by.liudchyk.audiotracks.database;

/**
 * Created by Admin on 24.12.2016.
 */

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

public class ConnectionPool {
    private static final Logger LOG = LogManager.getLogger();
    private static ConnectionPool pool;
    private static AtomicBoolean instanceCreated = new AtomicBoolean(false);
    private static ReentrantLock lock = new ReentrantLock();
    private ArrayBlockingQueue<ProxyConnection> connectionQueue;
    private static InitDB init;

    private ConnectionPool() {
        init = new InitDB();
        this.connectionQueue = new ArrayBlockingQueue<>(init.POOL_SIZE);
        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
        }catch (SQLException e){
            LOG.fatal(e);
            throw new RuntimeException(e);
        }
        int size = init.POOL_SIZE;
        for (int i = 0; i < size; i++) {
            try {
                Connection connection = DriverManager.getConnection(init.DATABASE, init.DB_LOGIN, init.DB_PASSWORD);
                ProxyConnection pc = new ProxyConnection(connection);
                this.connectionQueue.put(pc);
            } catch (SQLException | InterruptedException e) {
                LOG.warn("Connection wasn't add into connection queue",e);
            }
        }
    }

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

    public ProxyConnection getConnection() {
        ProxyConnection connection = null;
        try {
            connection = connectionQueue.take();
        } catch (InterruptedException e) {
            LOG.error(e);
        }
        return connection;
    }

    void closeConnection(ProxyConnection connection) {
        try {
            connectionQueue.put(connection);
        } catch (InterruptedException e) {
            LOG.error(e);
        }
    }

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