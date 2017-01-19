package by.liudchyk.audiotracks.database;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Created by Admin on 27.12.2016.
 */
class InitDB {
    static final Logger LOG = LogManager.getLogger();
    final ResourceBundle RESOURCE;
    final String DATABASE;
    final String DB_LOGIN;
    final int POOL_SIZE;
    final String DB_PASSWORD;

     InitDB() {
        try {
            RESOURCE = ResourceBundle.getBundle("properties.database");
            DATABASE = RESOURCE.getString("db.url");
            DB_LOGIN = RESOURCE.getString("db.user");
            POOL_SIZE = Integer.valueOf(RESOURCE.getString("db.poolsize"));
            DB_PASSWORD = RESOURCE.getString("db.password");
        } catch (NumberFormatException | MissingResourceException e) {
            LOG.fatal("Can't init database", e);
            throw new RuntimeException("Can't init database", e);
        }
    }
}
