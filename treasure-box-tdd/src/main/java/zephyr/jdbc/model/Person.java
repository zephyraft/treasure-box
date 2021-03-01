package zephyr.jdbc.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by zephyr on 2019-10-03.
 */
@Data
@AllArgsConstructor
public class Person {

    private Integer id;
    private String firstName;
    private String lastName;

}
