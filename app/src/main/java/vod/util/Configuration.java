package vod.util;

import java.io.File;

/**
 * Created by forDream on 2015-12-27.
 */
public class Configuration {
    private static String tempFolder;
    private static String currentFolder;

    public static String getTempFolder() {
        if (tempFolder == null)
            tempFolder = System.getProperty("java.io.tmpdir") + File.separator;
        return tempFolder;
    }

    public static String getCurrentFolder() {
        if (currentFolder == null)
            currentFolder = System.getProperty("user.dir") + File.separator;
        return currentFolder;
    }

    public static String getConfigFile(String filename) {
        return getCurrentFolder() + filename;
    }
}
