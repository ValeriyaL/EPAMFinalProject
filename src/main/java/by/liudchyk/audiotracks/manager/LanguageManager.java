package by.liudchyk.audiotracks.manager;

/**
 * Created by Admin on 23.12.2016.
 */

import java.util.Locale;
import java.util.ResourceBundle;

public class LanguageManager {
    private static final String RUS = "ru_RU";
    private static final String ENG = "en_US";
    private static final String FILE = "properties.content";
    private static ResourceBundle resourceBundle;

    private LanguageManager() {
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