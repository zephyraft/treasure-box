package zephyr.jackson.parse;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Iterator;


public class JacksonTreeNodeParseDemo {

    private static final Logger LOGGER = LoggerFactory.getLogger(JacksonTreeNodeParseDemo.class);
    private static final String jsonString = "{\"title\":\"Free Music Archive - Albums\",\"message\":\"\",\"errors\":[\"invalid or disabled api_key\"],\"http_status\":403,\"dataset\":[]}";

    public static void main(String[] args) throws IOException {
        // create an ObjectMapper instance.
        ObjectMapper mapper = new ObjectMapper();
        // use the ObjectMapper to read the json string and create a tree
        JsonNode node = mapper.readTree(jsonString);
        // lets see what type the node is
        System.out.println(node.getNodeType()); // prints OBJECT
        // is it a container
        System.out.println(node.isContainerNode()); // prints true
        // lets find out what fields it has
        Iterator<String> fieldNames = node.fieldNames();
        while (fieldNames.hasNext()) {
            String fieldName = fieldNames.next();
            System.out.println(fieldName);// prints title, message, errors,
            // total,
            // total_pages, page, limit, dataset
        }

        // we now know what elemets the container has. lets get the value for
        // one of them
        System.out.println(node.get("title").asText()); // prints
        // "Free Music Archive".

        // Lets look at the dataset now.
        JsonNode dataset = node.get("dataset");

        // what is its type?
        System.out.println(dataset.getNodeType()); // Prints ARRAY

        // so the dataset is an array. Lets iterate through the array and see
        // what each of the elements are
        Iterator<JsonNode> datasetElements = dataset.iterator();
        while (datasetElements.hasNext()) {
            JsonNode datasetElement = datasetElements.next();
            // what is its type
            System.out.println(datasetElement.getNodeType());// Prints Object
            // it is again a container . what are the elements ?
            Iterator<String> datasetElementFields = datasetElement.fieldNames();
            while (datasetElementFields.hasNext()) {
                String datasetElementField = datasetElementFields.next();
                System.out.println(datasetElementField); // prints album_id,
                // album_title,
                // album_handle,
                // album_url,
                // album_type,
                // artist_name,
                // artist_url,
                // album_producer,
                // album_engineer,
                // album_information,
                // album_date_released,
                // album_comments,
                // album_favorites,
                // album_tracks,
                // album_listens,
                // album_date_created,
                // album_image_file,
                // album_images

            }
            // break from the loop, since we just want to see the structure
            break;

        }
    }

}
