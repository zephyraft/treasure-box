package zephyr.generate;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

/**
 * Created by zephyr on 2019-06-27.
 */
public class JacksonTreeNodeDemo {
    private static final Logger LOGGER = LoggerFactory.getLogger(JacksonTreeNodeDemo.class);

    public static void main(String[] args) throws IOException {
        // Create the node factory that gives us nodes.
        JsonNodeFactory factory = new JsonNodeFactory(false);

        // create a json factory to write the treenode as json. for the example
        // we just write to console
        JsonFactory jsonFactory = new JsonFactory();
        ObjectMapper mapper = new ObjectMapper();

        try (Writer writer = new StringWriter();
             JsonGenerator generator = jsonFactory.createGenerator(writer)) {
            // the root node - album
            ObjectNode album = factory.objectNode();
            album.put("Album-Title", "Kind Of Blue");

            ArrayNode links = factory.arrayNode();
            links.add("link1").add("link2");
            album.set("links", links);

            ObjectNode artist = factory.objectNode();
            artist.put("Artist-Name", "Miles Davis");
            artist.put("birthDate", "26 May 1926");
            album.set("artist", artist);

            ObjectNode musicians = factory.objectNode();
            musicians.put("Julian Adderley", "Alto Saxophone");
            musicians.put("Miles Davis", "Trumpet, Band leader");
            album.set("musicians", musicians);

            mapper.writeTree(generator, album);
            String jsonString = writer.toString();
            LOGGER.info(jsonString);
        }
    }

}
