package zephyr.model.zoo;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * Created by zephyr on 2019-06-27.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, property = "@class")
@JsonSubTypes({@JsonSubTypes.Type(value = Lion.class, name = "lion"), @JsonSubTypes.Type(value = Elephant.class, name = "elephant")})
public class Animal {

    public String name;

}
