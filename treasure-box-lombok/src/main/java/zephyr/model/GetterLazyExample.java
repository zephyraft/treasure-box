package zephyr.model;

import lombok.Getter;

/**
 * Created by zephyr on 2019-06-30.
 */
public class GetterLazyExample {
    @Getter(lazy=true)
    private final double[] cached = expensive();

    private double[] expensive() {
        double[] result = new double[1000000];
        for (int i = 0; i < result.length; i++) {
            result[i] = Math.asin(i);
        }
        return result;
    }
}
