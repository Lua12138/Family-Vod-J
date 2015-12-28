package vod.util;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by forDream on 2015-12-27.
 */
public class CommandLine {
    public static String run(String command) {
        StringBuilder builder = new StringBuilder();
        try {
            Process process = Runtime.getRuntime().exec(command);
            InputStream is = process.getInputStream();

            byte[] buff = new byte[4096];
            int len = -1;
            while (-1 != (len = is.read(buff, 0, buff.length))) {
                builder.append(new String(buff, 0, len));
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return builder.toString();
    }
}
