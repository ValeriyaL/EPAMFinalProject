package by.liudchyk.audiotracks.database;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Created by Admin on 27.12.2016.
 */
public class InitDB {
    private static final Logger LOG = LogManager.getLogger();
    private final ResourceBundle RESOURSE;
    private final String DATABASE;
    private final String DB_LOGIN;
    private final String POOL_SIZE;
    private final String DB_PASSWORD;

    public InitDB(){
        try {
            RESOURSE = ResourceBundle.getBundle("properties.database");
            DATABASE = RESOURSE.getString("db.url");
            DB_LOGIN = RESOURSE.getString("db.user");
            POOL_SIZE = RESOURSE.getString("db.poolsize");
            DB_PASSWORD = RESOURSE.getString("db.password");
        }catch (MissingResourceException e){
            LOG.fatal("can't init database",e);
            throw new RuntimeException("Can't init database", e);
        }
    }

    public String getDatabase() {
        return DATABASE;
    }

    public String getDbLogin() {
        return DB_LOGIN;
    }

    public String getPoolSize() {
        return POOL_SIZE;
    }

    public String getDbPassword() {
        return DB_PASSWORD;
    }
}
