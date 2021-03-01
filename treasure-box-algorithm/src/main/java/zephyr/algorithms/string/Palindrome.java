package zephyr.algorithms.string;

import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;

/**
 * 回文串
 * Created by zephyr on 2020/6/15.
 */
@Slf4j
public class Palindrome {

    public static void main(String[] args) {
        log.info("{}", longestPalindromeLength("abccccdd"));
        log.info("{}", isPalindrome("A man, a plan, a canal: Panama"));
        log.info("{}", isPalindrome("race a car"));
        log.info("{}", longestPalindromeSubstring("babad"));
        log.info("{}", longestPalindromeSubstring("cbbd"));
        log.info("{}", longestPalindromeSubSeq("bbbab"));
        log.info("{}", longestPalindromeSubSeq("cbbd"));
    }

    /**
     * 最长回文串
     * 两种情况：
     * 1. 字符串长度为偶数（每个字符成对出现）
     * 2. 字符串长度为奇数（中间有一个任意字符）
     *
     * @param s 字符串
     * @return 回文串长度
     */
    public static int longestPalindromeLength(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        int count = 0;
        HashSet<Character> hashSet = new HashSet<>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (hashSet.contains(c)) {
                // 已经出现过
                hashSet.remove(c);
                count++;
            } else {
                // 还未出现过
                hashSet.add(c);
            }
        }
        return hashSet.isEmpty() ? count * 2 : count * 2 + 1;
    }

    /**
     * 验证回文串
     *
     * @param s 字符串
     * @return 是否是回文串
     */
    public static boolean isPalindrome(String s) {
        if (s == null) {
            return false;
        }
        if (s.length() == 0) {
            return true;
        }
        int f = 0; // 索引
        int l = s.length() - 1; // 索引

        while (f < l) {
            final char c1 = s.charAt(f);
            final char c2 = s.charAt(l);
            if (!Character.isLetterOrDigit(c1)) {
                f++;
            } else if (!Character.isLetterOrDigit(c2)) {
                l--;
            } else {
                if (Character.toLowerCase(c1) != Character.toLowerCase(c2)) {
                    return false;
                }
                f++;
                l--;
            }
        }

        return true;
    }

    /**
     * 最长回文串
     * 两种情况：
     * 1. 字符串长度为偶数（每个字符成对出现）
     * 2. 字符串长度为奇数（中间有一个任意字符）
     *
     * @param s 字符串
     * @return 最长回文串
     */
    public static String longestPalindromeSubstring(String s) {
        int index = 0;
        int len = 0;

        if (s.length() <= 1) {
            return s;
        }

        for (int i = 0; i < s.length() - 1; i++) {
            // 奇数情况
            int i1 = i;
            int i2 = i;
            while (i1 >= 0 && i2 < s.length() && s.charAt(i1) == s.charAt(i2)) {
                i1--;
                i2++;
            }
            // 找到了更长的回文串
            if (len < i2 - i1 - 1) {
                index = i1 + 1;
                len = i2 - i1 - 1;
            }

            // 偶数情况
            i1 = i;
            i2 = i + 1;
            while (i1 >= 0 && i2 < s.length() && s.charAt(i1) == s.charAt(i2)) {
                i1--;
                i2++;
            }
            if (len < i2 - i1 - 1) {
                index = i1 + 1;
                len = i2 - i1 - 1;
            }
        }
        return s.substring(index, index + len);
    }

    /**
     * 最长回文子序列
     * 子序列不要求连续
     *
     * bbbab -> bbbb -> 4
     * cbbd -> bb -> 2
     *
     * TODO read 动态规划
     *
     * @param s 字符串
     * @return 最长子序列长度
     */
    public static int longestPalindromeSubSeq(String s) {
        int len = s.length();
        int[][] dp = new int[len][len];
        for (int i = len - 1; i>= 0; i--) {
            dp[i][i] = 1;
            for (int j = i + 1; j < len; j++) {
                if (s.charAt(i) == s.charAt(j)) {
                    dp[i][j] = dp[i + 1][j - 1] + 2;
                } else {
                    dp[i][j] = Math.max(dp[i + 1][j], dp[i][j - 1]);
                }
            }
        }
        return dp[0][len - 1];
    }
}
