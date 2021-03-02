package zephyr.jackson.parse;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import zephyr.jackson.model.SceneRule;
import zephyr.jackson.model.album.Album;

import java.io.IOException;
import java.util.List;


@Slf4j
public class JacksonJavaObjectParseDemo {

    private static final String jsonString = "{\"title\":\"Free Music Archive - Albums\",\"message\":\"\",\"errors\":[\"invalid or disabled api_key\"],\"http_status\":403,\"dataset\":[]}";
    private static final String jsonArrayString = "[{\"sceneType\":\"1\",\"isFree\":true,\"percent\":\"0\"},{\"sceneType\":\"2\",\"isFree\":false,\"percent\":\"70\"}]";

    public static void main(String[] args) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        Album album = mapper.readValue(jsonString, Album.class);
        String title = album.getTitle();
        log.info(title);

        final List<SceneRule> sceneRule = mapper.readValue(jsonArrayString, new TypeReference<List<SceneRule>>() {});
        System.out.println(sceneRule.get(0));
        System.out.println(sceneRule.get(1));
        log.info("{}", sceneRule);

        final List<SceneRule> sceneRule2 = mapper.readValue("[]", new TypeReference<List<SceneRule>>() {});
        log.info("{}", sceneRule2);
    }

}
