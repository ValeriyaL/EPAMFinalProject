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
    private static ConnectionPool pool;
    private static AtomicBoolean instanceCreated = new AtomicBoolean(false);
    private static ReentrantLock lock = new ReentrantLock();
    private static final Logger LOG = LogManager.getLogger();
    private ArrayBlockingQueue<ProxyConnection> connectionQueue;
    private static final ResourceBundle RESOURSE;
    private static final String DATABASE;
    private static final String DB_LOGIN;
    private static final String POOL_SIZE;
    private static final String DB_PASSWORD;

    static {
        RESOURSE = ResourceBundle.getBundle("properties.database");
        DATABASE = RESOURSE.getString("db.url");
        DB_LOGIN = RESOURSE.getString("db.user");
        POOL_SIZE = RESOURSE.getString("db.poolsize");
        DB_PASSWORD = RESOURSE.getString("db.password");
    }

    private ConnectionPool(ArrayBlockingQueue<ProxyConnection> connectionQueue){
        this.connectionQueue = connectionQueue;
    }

    public static ConnectionPool getInstance(){
        if(!instanceCreated.get()) {
            lock.lock();
            try {
                if (pool == null) {
                    int size = Integer.valueOf(POOL_SIZE);
                    pool = new ConnectionPool(new ArrayBlockingQueue<>(size));
                    try {
                        DriverManager.registerDriver(new com.mysql.jdbc.Driver());
                        for (int i = 0; i < size; i++) {
                            Connection connection = DriverManager.getConnection(DATABASE, DB_LOGIN, DB_PASSWORD);
                            ProxyConnection pc = new ProxyConnection(connection);
                            pool.connectionQueue.put(pc);
                        }
                        instanceCreated.getAndSet(true);
                    } catch (SQLException | InterruptedException e) {
                        LOG.fatal(e);
                        throw new RuntimeException(e);
                    }
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
        }catch (InterruptedException e){
            LOG.error(e);
        }
        return connection;
    }

    void closeConnection(ProxyConnection connection) {
        try {
            connectionQueue.put(connection);
        } catch (InterruptedException e) {
            LOG.warn(e);
        }
    }

    public void closePool(){
        int size = Integer.valueOf(POOL_SIZE);
        try {
            for (int i = 0; i < size; i++) {
                connectionQueue.take().realClose();
            }
        }catch (SQLException | InterruptedException e) {
            LOG.error(e);
        }
    }
}