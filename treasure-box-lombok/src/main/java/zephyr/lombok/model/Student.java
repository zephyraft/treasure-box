package zephyr.lombok.model;

import lombok.*;


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
    private Integer age;

}
