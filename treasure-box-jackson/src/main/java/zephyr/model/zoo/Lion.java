package zephyr.model.zoo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by zephyr on 2019-06-27.
 */
public class Lion extends Animal {
    @JsonCreator
    public Lion(@JsonProperty("name") String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Lion: " + name;
    }
}
