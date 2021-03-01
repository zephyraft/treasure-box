package zephyr.algorithms.other;

import lombok.extern.slf4j.Slf4j;

/**
 * 斐波拉契
 * Created by zephyr on 2020/6/16.
 */
@Slf4j
public class JumpFloor {

    public static void main(String[] args) {
        log.info("{}", jumpFloor(5));
        log.info("{}", jumpFloorN(3));
    }

    /**
     * 青蛙一次可以跳一级台阶，也可以跳两级，求有多少种跳法
     * f(n) = f(n - 1) + f(n - 2)
     * @param n 跳n级台阶
     * @return 跳法
     */
    public static int jumpFloor(int n) {
        if (n <= 0) {
            return 0;
        }
        if (n == 1) {
            return 1;
        }
        if (n == 2) {
            return 2;
        }

        int first = 1;
        int second = 2;
        int third = 0;
        for (int i = 3; i <= n; i++) {
            third = first + second;
            first = second;
            second = third;
        }
        return third;
    }

    /**
     * 青蛙一次可以跳一级台阶，也可以跳n级，求有多少种跳法
     * f(n) = f(n - 1) + f(n - 2) + f(n - 3) + ... + f(1) + 1
     * f(n - 1) = f(n - 2) + f(n - 3) + ... + f(1) + 1
     * f(n) = 2 * f(n - 1)
     * f(1) = 1
     * f(2) = 2
     * f(3) = 4
     * f(4) = 8
     * f(n) = 2 ^ (n - 1)
     *
     * @param n 跳n级台阶
     * @return 跳法
     */
    public static int jumpFloorN(int n) {
        return 1 << --n; // 左移相当于*2
    }
}
