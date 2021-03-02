package zephyr.jackson.parse;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.cbor.CBORFactory;

import java.io.IOException;
import java.util.Base64;
import java.util.Map;


public class CborParseDemo {

    private static final String jsonString = "{\"120794\":{\"albumId\":\"120794\",\"campTagsSize\":0,\"clearingTypeId\":1,\"cooperationTypeId\":1,\"description\":\"每天更新1集更新太多了不会再更新了-付费专辑的更新说明看看吧\",\"freeTrackIds\":[371455,373293,373294,374132,374133,374134,467174,467438,844827,975779,975780,980120],\"freeTrackIdsIterator\":[371455,373293,373294,374132,374133,374134,467174,467438,844827,975779,975780,980120],\"freeTrackIdsSize\":12,\"isCampAlbum\":false,\"producerTypeId\":0,\"retailSaleModes\":[],\"retailSaleModesIterator\":[],\"retailSaleModesSize\":0,\"totalTrackCount\":30,\"vipSaleModes\":[],\"vipSaleModesIterator\":[],\"vipSaleModesSize\":0}}";

    public static void main(String[] args) throws IOException {
        CBORFactory f = new CBORFactory();
        ObjectMapper cborMapper = new ObjectMapper(f);
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> map = objectMapper.readValue(jsonString, new TypeReference<Map<String, Object>>() {});

        final String s = Base64.getEncoder().encodeToString(cborMapper.writeValueAsBytes(map));
        final String json = objectMapper.writeValueAsString(map);
        System.out.println(s);
        System.out.println("cbor length: " + s.length());
        System.out.println(json);
        System.out.println("json length: " + json.length());

        final Map<String, Object> stringObjectMap = cborMapper.readValue(Base64.getDecoder().decode(s), new TypeReference<Map<String, Object>>() {
        });

        System.out.println(stringObjectMap);
    }

}
