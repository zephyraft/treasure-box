package zephyr.practice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zephyr on 2020/6/3.
 */
@Slf4j
public class DeepCopyDemo {

    static ObjectMapper objectMapper = new ObjectMapper();
    static Map<Integer, StrategyService> serviceMap = new HashMap<>();

    static {
        serviceMap.put(111, new StrategyOneService());
        serviceMap.put(222, new StrategyTwoService());
    }

    public static void main(String[] args) throws JsonProcessingException {
        // key - appKey, value - 设备id列表
        Map<Integer, List<String>> appKeyMap = new HashMap<>();

        List<String> oneList = new ArrayList<>();
        oneList.add("device1");
        appKeyMap.put(111, oneList);
        List<String> twoList = new ArrayList<>();
        twoList.add("device2");
        twoList.add("device3");
        appKeyMap.put(222, twoList);
        // 创建消息
        List<Msg> msgList = new ArrayList<>();
        Msg msg = new Msg();
        msg.setDataId("abc");
        msg.setBody("hello");
        msgList.add(msg);

        for (int appKey : appKeyMap.keySet()) {
            // 根据appKey获取消息列表
            StrategyService strategyService = serviceMap.get(appKey);
            if (strategyService != null) {
                // 需要深复制msgList

                // 此处用json序列化后反序列化实现
                strategyService.sendMsg(objectMapper.readValue(objectMapper.writeValueAsString(msgList), new TypeReference<List<Msg>>() {}), appKeyMap.get(appKey));
            } else {
                log.info("appKey:{}, is not registered service", appKey);
            }
        }

    }




    private interface StrategyService {
        void sendMsg(List<Msg> msgList, List<String> deviceList);
    }

    private static class StrategyOneService implements StrategyService {
        @SneakyThrows
        @Override
        public void sendMsg(List<Msg> msgList, List<String> deviceList) {
            for (Msg msg : msgList) {
                msg.setDataId("oneService_" + msg.getDataId());
                log.info("{} {}", msg.getDataId(), objectMapper.writeValueAsString(deviceList));
            }
        }
    }

    private static class StrategyTwoService implements StrategyService {
        @SneakyThrows
        @Override
        public void sendMsg(List<Msg> msgList, List<String> deviceList) {
            for (Msg msg : msgList) {
                msg.setDataId("twoService_" + msg.getDataId());
                log.info("{} {}", msg.getDataId(), objectMapper.writeValueAsString(deviceList));
            }
        }
    }

    private static class Msg {
        private String dataId;
        private String body;

        public String getDataId() {
            return dataId;
        }

        public void setDataId(String dataId) {
            this.dataId = dataId;
        }

        public String getBody() {
            return body;
        }

        public void setBody(String body) {
            this.body = body;
        }
    }
}
