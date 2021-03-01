package zephyr.grokkingalgorithms;

/**
 * 阶乘
 */
public class Factorial {

    public static void main(String[] args) {
        System.out.println(recursiveFactorial(4));
    }

    private static int recursiveFactorial(int n) {
        if (n <= 0) {
            return 0;
        }
        if (n == 1) {
            return 1;
        } else {
            return n * recursiveFactorial(n - 1);
        }
    }

}
