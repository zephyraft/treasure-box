package zephyr.model;

import lombok.AccessLevel;
import lombok.ToString;
import lombok.Value;
import lombok.With;
import lombok.experimental.NonFinal;

/**
 * Created by zephyr on 2019-06-30.
 */
@Value
public class ValueExample {
    protected String[] tags;
    String name;
    double score;
    @With(AccessLevel.PACKAGE)
    @NonFinal
    int age;

    @ToString()
    @Value(staticConstructor="of")
    public static class Exercise<T> {
        String name;
        T value;
    }
}
