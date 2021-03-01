package zephyr.algorithms.string;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * O(M+N)
 * Created by zephyr on 2020/6/15.
 */
@Slf4j
public class KMP {

    public static void main(String[] args) {
        // abbaabbaababbaaba
        //     abbaaba
        //           abbaaba
        final List<Integer> search = search("abbaabbaababbaaba", "abbaaba");
        log.info("{}", search);
    }


    // 移动位数 = 已匹配的字符数 - 对应的部分匹配值
    // 在文本 text 中寻找模式串 pattern，返回所有匹配的位置开头
    public static List<Integer> search(String text, String pattern) {
        List<Integer> positions = new ArrayList<>();
        int[] maxMatchLengths = calculateMaxMatchLengths(pattern);
        int count = 0;
        for (int i = 0; i < text.length(); i++) {
            while (count > 0 && pattern.charAt(count) != text.charAt(i)) {
                count = maxMatchLengths[count - 1];
            }
            if (pattern.charAt(count) == text.charAt(i)) {
                count++;
            }
            if (count == pattern.length()) {
                positions.add(i - pattern.length() + 1);
                count = maxMatchLengths[count - 1];
            }
        }
        return positions;
    }

    /**
     * 部分匹配表Partial Match Table（PMT）
     * 构造模式串 pattern 的最大匹配数表
     * 复杂度是线性的
     *
     * char     abababca        abbaaba     ABCDABD
     * value    00123401        0001121     0000120
     * PMT中的值是字符串的前缀集合与后缀集合的交集中最长元素的长度
     */
    private static int[] calculateMaxMatchLengths(String pattern) {
        // 根据模式串的长度，构造PMT数组
        int[] maxMatchLengths = new int[pattern.length()];
        int maxLength = 0;
        for (int i = 1; i < pattern.length(); i++) {
            while (maxLength > 0 && pattern.charAt(maxLength) != pattern.charAt(i)) {
                maxLength = maxMatchLengths[maxLength - 1];
            }
            if (pattern.charAt(maxLength) == pattern.charAt(i)) {
                maxLength++;
            }
            maxMatchLengths[i] = maxLength;
        }
        return maxMatchLengths;
    }

}
