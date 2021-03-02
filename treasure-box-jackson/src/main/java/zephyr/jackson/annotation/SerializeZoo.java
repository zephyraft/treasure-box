package zephyr.jackson.annotation;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import zephyr.jackson.model.zoo.Elephant;
import zephyr.jackson.model.zoo.Lion;
import zephyr.jackson.model.zoo.Zoo;

import java.io.IOException;

@Slf4j
public class SerializeZoo {

    public static void main(String[] args) throws IOException {
        Zoo zoo = new Zoo("London Zoo", "London");
        Lion lion = new Lion("Simba");
        Elephant elephant = new Elephant("Manny");
        zoo.addAnimal(elephant).add(lion);
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(zoo);
        log.info(jsonString);

        // 反序列化
        Zoo zooFromJson = mapper.readValue(jsonString, Zoo.class);
        log.info("{}", zooFromJson);
    }

}
