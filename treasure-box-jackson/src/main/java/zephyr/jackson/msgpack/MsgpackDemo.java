package zephyr.jackson.msgpack;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import org.msgpack.jackson.dataformat.MessagePackFactory;

import java.io.IOException;
import java.time.Instant;
import java.util.Optional;

public class MsgpackDemo {

    public static void main(String[] args) throws IOException {
        // Instantiate ObjectMapper for MessagePack
        ObjectMapper objectMapper =
                new ObjectMapper(new MessagePackFactory())
                        .registerModule(new Jdk8Module())
                        .registerModule(new JavaTimeModule())
                        .registerModule(new ParameterNamesModule()) // compile时需要加上 -parameters 参数
                        .findAndRegisterModules();

        // Serialize a Java object to byte array
        ExamplePojo pojo = new ExamplePojo("abc", "komamitsu", 180.54D, Instant.now(), Optional.of(123L));
        byte[] bytes = objectMapper.writeValueAsBytes(pojo);

        // Deserialize the byte array to a Java object
        ExamplePojo deserialized = objectMapper.readValue(bytes, ExamplePojo.class);
        System.out.println(deserialized); // => komamitsu
        System.out.println(deserialized.getAaa()); // => komamitsu
        System.out.println(deserialized.getName()); // => komamitsu
        System.out.println(deserialized.getHeight()); // => komamitsu
        System.out.println(deserialized.getInstant()); // => komamitsu
        System.out.println(deserialized.getOptional()); // => komamitsu
    }

    public static class ExamplePojo {
        private final String aaa;
        private String name;
        private double height;
        private Instant instant;
        private Optional<Long> optional;

        public ExamplePojo(
                String aaa,
                String name,
                double height,
                Instant instant,
                Optional<Long> optional
        ) {
            this.aaa = aaa;
            this.name = name;
            this.height = height;
            this.instant = instant;
            this.optional = optional;
        }

        public String getAaa() {
            return aaa;
        }

        public Optional<Long> getOptional() {
            return optional;
        }

        public void setOptional(Optional<Long> optional) {
            this.optional = optional;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public double getHeight() {
            return height;
        }

        public void setHeight(double height) {
            this.height = height;
        }

        public Instant getInstant() {
            return instant;
        }

        public void setInstant(Instant instant) {
            this.instant = instant;
        }

        @Override
        public String toString() {
            return "ExamplePojo{" +
                    "name='" + name + '\'' +
                    ", height=" + height +
                    ", instant=" + instant +
                    ", optional=" + optional +
                    '}';
        }
    }
}
