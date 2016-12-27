package by.liudchyk.audiotracks.manager;

/**
 * Created by Admin on 23.12.2016.
 */
import java.util.Locale;
import java.util.ResourceBundle;

public class LanguageManager {
    private static ResourceBundle resourceBundle;

    private LanguageManager() {
    }

    public static String getProperty(String key, String lang) {
        if(lang!=null) {
            switch (lang) {
                case "ru_RU":
                    Locale locale = new Locale("ru", "RU");
                    resourceBundle = ResourceBundle.getBundle("properties.content", locale);
                    break;
                case "en_US":
                    locale = new Locale("en", "US");
                    resourceBundle = ResourceBundle.getBundle("properties.content", locale);
                    break;
                default:
                    resourceBundle = ResourceBundle.getBundle("properties.content");
            }
        }else{
            resourceBundle = ResourceBundle.getBundle("properties.content");
        }
        return resourceBundle.getString(key);
    }
}