package zephyr.algorithms.string;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by zephyr on 2020/6/15.
 */
@Slf4j
public class ReplaceSpace {

    public static void main(String[] args) {
        log.info("{}", replaceSpace("We Are Happy"));
        log.info("{}", replaceSpaceByAPI("We Are Happy"));
    }

    public static String replaceSpace(String s) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            final char c = s.charAt(i);
            if (c == ' ') {
                stringBuilder.append("%20");
            } else {
                stringBuilder.append(c);
            }
        }
        return stringBuilder.toString();
    }

    public static String replaceSpaceByAPI(String s) {
        return s.replaceAll("\\s", "%20");
    }
}
