package zephyr.jackson.generate;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

@Slf4j
public class JacksonStreamDemo {

    public static void main(String[] args) throws IOException {
        JsonFactory factory = new JsonFactory();
        try (Writer writer = new StringWriter();
             JsonGenerator generator = factory.createGenerator(writer)) {
            // start writing with
            generator.writeStartObject();
            generator.writeFieldName("title");
            generator.writeString("Free Music Archive - Albums");
            generator.writeFieldName("dataset");
            // start an array
            generator.writeStartArray();
            generator.writeStartObject();
            generator.writeStringField("album_title", "A.B.A.Y.A.M");
            generator.writeEndObject();
            generator.writeEndArray();
            generator.writeEndObject();
            // flush buffer to writer
            generator.flush();
            String jsonString = writer.toString();
            log.info(jsonString);
        }
    }

}
