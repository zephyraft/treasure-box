package zephyr.model.zoo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by zephyr on 2019-06-27.
 */
public class Elephant extends Animal {

    @JsonCreator
    public Elephant(@JsonProperty("name") String name) {
        super.name = name;
    }

    @Override
    public String toString() {
        return "Elephant : " + name;
    }

}
