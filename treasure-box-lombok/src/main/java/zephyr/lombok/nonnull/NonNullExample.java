package zephyr.lombok.nonnull;

import lombok.Getter;
import lombok.NonNull;
import zephyr.lombok.model.Student;


@Getter
public class NonNullExample {
    private String name;

    public NonNullExample(@NonNull Student student) {
        this.name = student.getName();
    }
}
