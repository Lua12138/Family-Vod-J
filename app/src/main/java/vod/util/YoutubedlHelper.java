package vod.util;

/**
 * Created by forDream on 2015-12-27.
 */
public class YoutubedlHelper {
    private static final String CMD_LIST_COUNT = "youtube-dl --get-id \"%s\"";
    private static final String CMD_LIST_JSON_ALL = "youtube-dl -j \"%s\"";
    private static final String CMD_LIST_JSON_INDEX = "youtube-dl --playlist-start %d --playlist-end %d -j \"%s\"";

    /**
     * @param url
     * @return the count of playlist, return -1 if fail.
     */
    public static int getPlaylistCount(String url) {
        String cmd = String.format(CMD_LIST_COUNT, url);
        String result = CommandLine.run(cmd);
        if (result == null)
            return -2;

        if (result.toLowerCase().indexOf("error") > 0)
            return -1;
        else {
            int c = 0;
            for (int i = 0; i < result.length(); i++) {
                if (result.charAt(i) == '\n')
                    c++;
            }
            return c;
        }
    }

    public static String getVideoInfo(String url) {
        String cmd = String.format(CMD_LIST_JSON_ALL, url);
        String result = CommandLine.run(cmd);

        return result;
    }

    public static String getVideoInfo(int index, String url) {
        String cmd = String.format(CMD_LIST_JSON_INDEX, index, index, url);
        String result = CommandLine.run(cmd);
        return result;
    }
}
