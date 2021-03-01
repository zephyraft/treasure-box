package zephyr.algorithms.string;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by zephyr on 2020/6/15.
 */
@Slf4j
public class BracketMatching {

    public static void main(String[] args) {
        log.info("{}", bracketMatchingDepth("(())"));
    }

    /**
     * (()) -> 2
     *
     * @return 深度
     */
    public static int bracketMatchingDepth(String s) {
        int count = 0;
        int maxDepth = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') {
                count++;
            } else {
                count--;
            }
            maxDepth = Math.max(count, maxDepth);
        }
        return maxDepth;
    }

}
