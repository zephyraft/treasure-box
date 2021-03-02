package zephyr.jackson.model.zoo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;


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
