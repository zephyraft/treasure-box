package zephyr.javassist.math;

public class HelloMath {

    private static final double PI = 3.14159D;

    //S = πr²
    public double calculateCircularArea(int r) {
        return PI * r * r;
    }

    //S = a + b
    public double sumOfTwoNumbers(double a, double b) {
        return a + b;
    }

}
