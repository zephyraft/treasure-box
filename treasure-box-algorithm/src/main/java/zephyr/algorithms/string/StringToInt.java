package zephyr.algorithms.string;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by zephyr on 2020/6/15.
 */
@Slf4j
public class StringToInt {

    public static void main(String[] args) {
        log.info("{}", stringToInt("-12312312"));
        log.info("{}", stringToInt("abc"));
        log.info("{}", stringToInt("3a1bc"));
        log.info("{}", stringToInt(""));
        log.info("{}", stringToInt("12312"));
        log.info("{}", stringToInt("+122"));
    }

    public static int stringToInt(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        final char firstChar = s.charAt(0);
        int symbolFlag = 0;
        if (firstChar == '+') {
            symbolFlag = 1;
        } else if (firstChar == '-') {
            symbolFlag = 2;
        }
        int result = 0;
        for (int i = symbolFlag == 0 ? 0 : 1; i < s.length(); i++) {
            if (Character.isDigit(s.charAt(i))) {
                int temp = s.charAt(i) - '0';
                result = result * 10 + temp;
            } else {
                return 0;
            }
        }
        return symbolFlag != 2 ? result : -result;
    }

}
