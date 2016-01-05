package vod.util;

import org.slf4j.Logger;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by forDream on 2015-12-27.
 */
public class AppProperty {
    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(AppProperty.class);

    private static final Properties properties;

    private static final String P_KEY_WEBROOT = "web";
    private static final String P_KEY_VLC = "vlc";
    public static final String P_KEY_WEBACCESS = "accessurl";

    public static final String P_KEY_IoC = "springcontext";

    static {
        properties = new Properties();
        // default value
        properties.put(P_KEY_WEBROOT, "../web/");
        File f = new File(Configuration.getConfigFile("settings.properties"));
        if (f.exists()) {
            try {
                logger.info("Property file exists, load it");
                properties.clear(); // clean default value
                InputStream is = new FileInputStream(f);
                properties.load(is);
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        for (Object key : properties.keySet()) {
            logger.debug(key + " -> " + properties.get(key));
        }

        logger.debug("AppProperty init finish.");
    }

    public static String getWebRoot() {
        return properties.getProperty(P_KEY_WEBROOT);
    }

    public static String getVlcRoot() {
        return properties.getProperty(P_KEY_VLC);
    }

    /**
     * save ioc, invoke once only
     *
     * @param context
     */
    public static void setSpringContext(AnnotationConfigApplicationContext context) {
        if (null == properties.get(P_KEY_IoC))
            properties.put(P_KEY_IoC, context);
    }

    public static AnnotationConfigApplicationContext getSpringContext() {
        return (AnnotationConfigApplicationContext) properties.get(P_KEY_IoC);
    }

    public static String userDir() {
        return System.getProperty("user.dir");
    }

    public static String get(String key) {
        return properties.getProperty(key);
    }

    public static void set(String key, String value) {
        properties.put(key, value);
    }

    public static boolean isDebug() {
        return true;
    }
}
