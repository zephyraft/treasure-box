package zephyr.model.zoo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zephyr on 2019-06-27.
 */
public class Zoo {

    public String name;
    public String city;
    public List<Animal> animals = new ArrayList<>();

    @JsonCreator
    public Zoo(@JsonProperty("name") String name, @JsonProperty("city") String city) {
        this.name = name;
        this.city = city;
    }

    public List<Animal> addAnimal(Animal animal) {
        animals.add(animal);
        return animals;
    }
}
