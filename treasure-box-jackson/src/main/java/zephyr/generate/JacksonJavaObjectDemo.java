package zephyr.generate;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import com.fasterxml.jackson.databind.introspect.AnnotatedField;
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import zephyr.model.album.Album;
import zephyr.model.album.Artist;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zephyr on 2019-06-27.
 */
public class JacksonJavaObjectDemo {

    private static final Logger LOGGER = LoggerFactory.getLogger(JacksonJavaObjectDemo.class);

    // 线程安全
    private static final ObjectMapper objectMapper = new ObjectMapper();

    static {
        // 使JSON在视觉上更具可读性，生产环境请勿使用
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        // 使用自然顺序按键排列Map
        objectMapper.configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, false);
        // 日期格式化
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd MMM yyyy");
        objectMapper.setDateFormat(outputFormat);
        // 自定义key
        objectMapper.setPropertyNamingStrategy(new PropertyNamingStrategy() {
            @Override
            public String nameForField(MapperConfig<?> config, AnnotatedField field, String defaultName) {
                if (field.getFullName().equals("zephyr.model.Artist#name")) {
                    return "Artist-Name";
                }
                return super.nameForField(config, field, defaultName);
            }

            @Override
            public String nameForGetterMethod(MapperConfig<?> config, AnnotatedMethod method, String defaultName) {
                if (method.getAnnotated().getDeclaringClass().equals(Album.class) && defaultName.equals("title")) {
                    return "Album-Title";
                }
                return super.nameForGetterMethod(config, method, defaultName);
            }
        });
        // 忽略空值
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
    }

    public static void main(String[] args) throws JsonProcessingException, ParseException {
        List<String> songs = new ArrayList<>();
        songs.add("So What");
        songs.add("Flamenco Sketches");
        songs.add("Freddie Freeloader");

        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        Artist artist = new Artist();
        artist.name = "Miles Davis";
        artist.birthDate = format.parse("26-05-1926");

        Album album = new Album();
        album.setTitle("Kind Of Blue");
        album.setLinks(new String[] { "link1", "link2" });
        album.setSongs(songs);
        album.setArtist(artist);
        album.addMusician("Miles Davis", "Trumpet, Band leader");
        album.addMusician("Julian Adderley", "Alto Saxophone");
        album.addMusician("Paul Chambers", "double bass");

        String jsonString = objectMapper.writeValueAsString(album);
        LOGGER.info(jsonString);



    }

}
