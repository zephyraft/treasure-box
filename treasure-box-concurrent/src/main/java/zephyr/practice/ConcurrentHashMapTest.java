package zephyr.practice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by zephyr on 2020/6/3.
 */
@Slf4j
public class ConcurrentHashMapTest {

    static ObjectMapper objectMapper = new ObjectMapper();

    static ConcurrentHashMap<String, List<String>> map = new ConcurrentHashMap<>();

    public static void main(String[] args) {
        Thread threadOne = new Thread(() -> {
            List<String> list = new ArrayList<>();
            list.add("device1");
            list.add("device2");
            final List<String> oldList = map.putIfAbsent("topic1", list);
            if (oldList != null) {
                oldList.addAll(list);
            }
            try {
                log.info(objectMapper.writeValueAsString(map));
            } catch (JsonProcessingException e) {
                log.error(e.getMessage(), e);
            }
        });

        Thread threadTwo = new Thread(() -> {
            List<String> list = new ArrayList<>();
            list.add("device3");
            list.add("device4");
            final List<String> oldList = map.putIfAbsent("topic1", list);
            if (oldList != null) {
                oldList.addAll(list);
            }
            try {
                log.info(objectMapper.writeValueAsString(map));
            } catch (JsonProcessingException e) {
                log.error(e.getMessage(), e);
            }
        });

        Thread threadThree = new Thread(() -> {
            List<String> list = new ArrayList<>();
            list.add("device5");
            list.add("device6");
            final List<String> oldList = map.putIfAbsent("topic2", list);
            if (oldList != null) {
                oldList.addAll(list);
            }
            try {
                log.info(objectMapper.writeValueAsString(map));
            } catch (JsonProcessingException e) {
                log.error(e.getMessage(), e);
            }
        });

        threadOne.start();
        threadTwo.start();
        threadThree.start();
    }
}
