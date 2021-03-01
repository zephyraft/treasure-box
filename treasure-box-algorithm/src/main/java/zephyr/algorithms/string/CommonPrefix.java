package zephyr.algorithms.string;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

/**
 * Created by zephyr on 2020/6/15.
 */
@Slf4j
public class CommonPrefix {

    public static void main(String[] args) {
        log.info("{}", findCommonPrefix(new String[]{"customer", "car", "cat"}));
        log.info("{}", findCommonPrefix(new String[]{}));
        log.info("{}", findCommonPrefix(null));
        log.info("{}", findCommonPrefix(new String[]{"customer", "car", null}));
        log.info("{}", findCommonPrefix(new String[]{"customer"}));
        log.info("{}", findCommonPrefix(new String[]{"customer", "customer"}));
        log.info("{}", findCommonPrefix(new String[]{"customer", "b"}));
    }

    public static String findCommonPrefix(String[] strings) {
        // 校验参数
        if (!arrayLegal(strings)) {
            return "";
        }
        // 先排序
        Arrays.sort(strings);
        // 再比较第一个和最后一个元素
        String first = strings[0];
        String last = strings[strings.length - 1];
        StringBuilder temp = new StringBuilder();
        int i = 0;
        while (i < first.length()) {
            if (first.charAt(i) == last.charAt(i)) {
                temp.append(first.charAt(i));
                i++;
            } else {
                break;
            }
        }
        return temp.toString();
    }

    private static boolean arrayLegal(String[] strings) {
        if (strings == null || strings.length == 0) {
            return false;
        } else {
            for (String string : strings) {
                if (string == null || string.length() == 0) {
                    return false;
                }
            }
        }
        return true;
    }

}
