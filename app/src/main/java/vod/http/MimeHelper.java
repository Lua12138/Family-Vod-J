package vod.http;

import eu.medsea.mimeutil.MimeType;
import eu.medsea.mimeutil.MimeUtil;

import java.io.File;
import java.util.Collection;
import java.util.Iterator;

/**
 * Created by forDream on 2015-12-26.
 */
public class MimeHelper {
    public static String getMime(String url) {
        MimeUtil.registerMimeDetector("eu.medsea.mimeutil.detector.MagicMimeMimeDetector");
        File file = new File(url);

        if (file.exists() && !file.isDirectory()) {
            Collection<MimeType> collection = MimeUtil.getMimeTypes(file);
            for (Iterator<MimeType> iterator = collection.iterator(); iterator.hasNext(); ) {
                MimeType type = iterator.next();
                return type.toString();
            }
        }
        return "unknown";
    }
}
