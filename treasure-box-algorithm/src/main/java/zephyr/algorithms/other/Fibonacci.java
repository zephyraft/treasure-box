package zephyr.algorithms.other;

import lombok.extern.slf4j.Slf4j;

/**
 * 斐波拉契
 * Created by zephyr on 2020/6/16.
 */
@Slf4j
public class Fibonacci {

    public static void main(String[] args) {
        log.info("{}", fibonacci(38));
        log.info("{}", fibonacciRecursive(38));
    }

    public static int fibonacciRecursive(int index) {
        if (index <= 0) {
            return 0;
        }

        if (index == 1 || index == 2) {
            return 1;
        }

        return fibonacciRecursive(index - 2) + fibonacciRecursive(index - 1);
    }

    public static int fibonacci(int index) {
        if (index <= 0) {
            return 0;
        }

        if (index == 1 || index == 2) {
            return 1;
        }

        int first = 1;
        int second = 1;
        int third = 0;
        for (int i = 3; i <= index; i++) {
            third = first + second;
            first = second;
            second = third;
        }
        return third;
    }

}
