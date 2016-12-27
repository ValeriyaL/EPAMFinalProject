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

    public static void InitDatabase(String db, String login, String size, String pass) {
        try {
            ResourceBundle RESOURSE = ResourceBundle.getBundle("properties.database");
            db = RESOURSE.getString("db.url");
            login = RESOURSE.getString("db.user");
            size = RESOURSE.getString("db.poolsize");
            pass = RESOURSE.getString("db.password");
        } catch (MissingResourceException e) {
            LOG.fatal("can't init database", e);
        }
    }
}
