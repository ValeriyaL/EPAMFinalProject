package test.by.liudchyk.audiotracks.database;

import by.liudchyk.audiotracks.database.ConnectionPool;
import by.liudchyk.audiotracks.database.ProxyConnection;
import org.junit.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;


/**
 * Created by Admin on 21.01.2017.
 */
public class ConnectionPoolTest {
    private static ConnectionPool pool;
    private static ArrayList<ProxyConnection> connections;

    @BeforeClass
    public static void initConnectionPool() {
        pool = ConnectionPool.getInstance();
    }

    @Before
    public void initConnections() {
        connections = new ArrayList<>();
    }

    @After
    public void destroyConnections(){
        connections.clear();
    }

    @AfterClass
    public static void closeConnectionPool() {
        pool.closePool();
    }

    @Test
    public void checkTakeConnection() throws SQLException {
        int expected = 10;
        for (int i = 0; i < expected; i++) {
            connections.add(pool.takeConnection());
        }
        int actual = connections.size();
        for (int i=0;i<actual;i++){
            connections.get(i).close();
        }
        Assert.assertEquals(expected,actual);
    }

    @Test
    public void checkPutConnection() throws SQLException{
        ProxyConnection connectionFirst = pool.takeConnection();
        connectionFirst.close();
        ProxyConnection connectionSecond;
        for(int i=0;i<9;i++) {
            connectionSecond = pool.takeConnection();
            connectionSecond.close();
        }
        connectionSecond = pool.takeConnection();
        connectionSecond.close();
        Assert.assertEquals(connectionFirst,connectionSecond);
    }
}
