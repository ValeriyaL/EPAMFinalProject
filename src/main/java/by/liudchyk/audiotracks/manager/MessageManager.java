package by.liudchyk.audiotracks.manager;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Class {@code MessageManager} is used to get message
 * in user's language
 *
 * @author LiudchykValeriya
 */
public class MessageManager {
    private static final String RUS = "ru_RU";
    private static final String ENG = "en_US";
    private static final String FILE = "properties.content";
    private static ResourceBundle resourceBundle;

    private MessageManager() {
    }

    public static String getProperty(String key, String lang) {
        if (lang != null) {
            switch (lang) {
                case RUS:
                    Locale locale = new Locale("ru", "RU");
                    resourceBundle = ResourceBundle.getBundle(FILE, locale);
                    break;
                case ENG:
                    locale = new Locale("en", "US");
                    resourceBundle = ResourceBundle.getBundle(FILE, locale);
                    break;
                default:
                    resourceBundle = ResourceBundle.getBundle(FILE);
            }
        } else {
            resourceBundle = ResourceBundle.getBundle(FILE);
        }
        return resourceBundle.getString(key);
    }
}