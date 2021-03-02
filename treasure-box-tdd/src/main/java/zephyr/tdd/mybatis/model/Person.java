package zephyr.tdd.mybatis.model;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class Person {

    private Integer id;
    private String firstName;
    private String lastName;

}
