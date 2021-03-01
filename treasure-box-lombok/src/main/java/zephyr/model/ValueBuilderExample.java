package zephyr.model;

import lombok.*;
import lombok.experimental.NonFinal;

/**
 * Created by zephyr on 2019-06-30.
 */
@Value
@Builder
public class ValueBuilderExample {
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
