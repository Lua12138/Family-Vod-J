package vod.util;

import java.io.File;

/**
 * Created by forDream on 2015-12-27.
 */
public class Configuration {
    private static String tempFolder;

    public static String getTempFolder() {
        if (tempFolder == null)
            tempFolder = System.getProperty("java.io.tmpdir") + File.separator;
        return tempFolder;
    }
}
