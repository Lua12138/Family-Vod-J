package vod.http;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vod.util.AppProperty;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by forDream on 2015-12-26.
 */
public class MimeHelper {
    private static Logger logger = LoggerFactory.getLogger(MimeHelper.class);

    public static Map<String, String> map;

    static {
        String dir = AppProperty.userDir() + File.separator;
        ObjectMapper objectMapper = new ObjectMapper();
        File config = new File(dir + "mime.json");
        boolean bool = false;
        if (config.exists()) {
            try {
                map = objectMapper.readValue(config, HashMap.class);
                bool = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (!bool) {
            logger.error("load config error");
            throw new Error("init mime helper fail.");
        }
    }

    public static String getMime(String file) {
        int dotPos = file.lastIndexOf(".");
        String ext = file.substring(dotPos + 1).toLowerCase();
        String mime = map.get(ext);
        if (mime == null)
            mime = "application/octet-stream";
        return mime;
    }

    public static String getMime(File file) {
        return getMime(file.getAbsolutePath());
    }
}
