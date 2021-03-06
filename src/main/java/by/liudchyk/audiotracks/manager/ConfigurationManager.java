package by.liudchyk.audiotracks.manager;

/**
 * Class {@code ConfigurationManager} is used to get path to pages
 *
 * @author LiudchykValeriya
 */

import java.util.ResourceBundle;

public class ConfigurationManager {
    private final static ResourceBundle resourceBundle = ResourceBundle.getBundle("properties.config");

    public static final String PATH_TRACK = "path.page.track";

    private ConfigurationManager() {
    }

    public static String getProperty(String key) {
        return resourceBundle.getString(key);
    }
}