package zephyr.model;

import lombok.*;

/**
 * Created by zephyr on 2019-06-30.
 */
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor // 搭配 final/ @NonNull 使用
public class Student {

    @Getter
    @Setter
    private String name;

    @Getter(AccessLevel.PROTECTED)
    @Setter
    @NonNull
    private int age;

}
