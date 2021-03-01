package zephyr.parse;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by zephyr on 2019-06-27.
 */
public class JacksonStreamParseDemo {

    private static final Logger LOGGER = LoggerFactory.getLogger(JacksonStreamParseDemo.class);
    private static final String jsonString = "{\"title\":\"Free Music Archive - Albums\",\"message\":\"\",\"errors\":[\"invalid or disabled api_key\"],\"http_status\":403,\"dataset\":[]}";

    public static void main(String[] args) throws IOException {
        JsonFactory factory = new JsonFactory();
        JsonParser parser = factory.createParser(jsonString);

        // continue parsing the token till the end of input is reached
        while (!parser.isClosed()) {
            // get the token
            JsonToken token = parser.nextToken();
            // if its the last token then we are done
            if (token == null) {
                break;
            }
            // we want to look for a field that says dataset

            if (JsonToken.FIELD_NAME.equals(token) && "dataset".equals(parser.getCurrentName())) {
                // we are entering the datasets now. The first token should be
                // start of array
                token = parser.nextToken();
                if (!JsonToken.START_ARRAY.equals(token)) {
                    // bail out
                    break;
                }
                // each element of the array is an album so the next token
                // should be
                token = parser.nextToken();
                if (!JsonToken.START_OBJECT.equals(token)) {
                    break;
                }
                // we are now looking for a field that says "album_title". We
                // continue looking till we find all such fields. This is
                // probably not a best way to parse this json, but this will
                // suffice for this example.
                while (true) {
                    token = parser.nextToken();
                    if (token == null)
                        break;
                    if (JsonToken.FIELD_NAME.equals(token) && "album_title".equals(parser.getCurrentName())) {
                        token = parser.nextToken();
                        System.out.println(parser.getText());
                    }

                }

            }

        }

    }

}
