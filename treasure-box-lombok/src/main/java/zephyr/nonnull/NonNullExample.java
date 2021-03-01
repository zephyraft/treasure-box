package zephyr.nonnull;

import lombok.Getter;
import lombok.NonNull;
import zephyr.model.Student;

/**
 * Created by zephyr on 2019-06-30.
 */
@Getter
public class NonNullExample {
    private String name;

    public NonNullExample(@NonNull Student student) {
        this.name = student.getName();
    }
}
