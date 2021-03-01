package zephyr.annotation;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import zephyr.model.zoo.Elephant;
import zephyr.model.zoo.Lion;
import zephyr.model.zoo.Zoo;

import java.io.IOException;

/**
 * Created by zephyr on 2019-06-27.
 */
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
