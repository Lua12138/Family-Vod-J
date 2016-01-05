package vod.util;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Created by forDream on 2016-01-04.
 */
public class I18nHelper {
    protected final static Locale locale;
    protected final static ResourceBundle bundle;

    static {
        locale = Locale.getDefault();
        bundle = ResourceBundle.getBundle("message", locale);
    }

    public static String getString(Object object, String key) {
        key = object.getClass().getName() + "." + key;
        return bundle.getString(key);
    }
}
