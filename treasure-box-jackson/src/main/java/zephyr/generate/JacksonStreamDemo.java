package zephyr.generate;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

/**
 * Created by zephyr on 2019-06-27.
 */
public class JacksonStreamDemo {

    private static final Logger LOGGER = LoggerFactory.getLogger(JacksonStreamDemo.class);

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
            LOGGER.info(jsonString);
        }
    }

}
