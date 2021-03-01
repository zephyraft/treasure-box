import org.redisson.Redisson;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import java.io.IOException;

public class RedissonDemo {

    public static void main(String[] args) throws IOException {
        // 1. Create config object
        Config config = new Config();
        config.useSingleServer().setAddress("redis://127.0.0.1:6379");
//        config.useClusterServers()
//                // use "rediss://" for SSL connection
//                .addNodeAddress("redis://127.0.0.1:6379");
        // or read config from file
//        config = Config.fromYAML(new File("config-file.yaml"));
        // 2. Create Redisson instance

        // Sync and Async API
        RedissonClient redisson = Redisson.create(config);
        // Reactive API
//        RedissonReactiveClient redissonReactive = Redisson.createReactive(config);

        // 3. Get Redis based implementation of java.util.concurrent.ConcurrentMap
        RMap<String, String> map = redisson.getMap("myMap");
//        RMapReactive<String, String> mapReactive = redissonReactive.getMap("myMap");
        map.put("1", "2");
        System.out.println(map.get("1"));

        redisson.shutdown();
    }

}
