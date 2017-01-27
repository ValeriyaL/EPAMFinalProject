package test.by.liudchyk.audiotracks.database;

import by.liudchyk.audiotracks.database.ConnectionPool;
import by.liudchyk.audiotracks.database.ProxyConnection;
import org.junit.*;

import java.sql.SQLException;
import java.util.ArrayList;


/**
 * Class {@code ConnectionPoolTest} is used to test
 * taking and putting connections from and into pool
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
    public void destroyConnections() {
        connections.clear();
    }

    @AfterClass
    public static void closeConnectionPool() {
        pool.closePool();
    }

    @Test
    public void checkTakeConnection() throws SQLException {
        connections.add(pool.takeConnection());
        Assert.assertNotNull(connections.get(0));
        connections.get(0).close();
    }

    @Test
    public void checkPutConnection() throws SQLException {
        ProxyConnection connectionFirst = pool.takeConnection();
        connectionFirst.close();
        ProxyConnection connectionSecond;
        for (int i = 0; i < 9; i++) {
            connectionSecond = pool.takeConnection();
            connectionSecond.close();
        }
        connectionSecond = pool.takeConnection();
        connectionSecond.close();
        Assert.assertEquals(connectionFirst, connectionSecond);
    }
}
