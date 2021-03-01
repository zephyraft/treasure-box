import redis.clients.jedis.Jedis;

public class RedisConnectionTest {

    public static void main(String[] args) {
//        Jedis jedis = new Jedis("172.20.68.233", 6379);
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        jedis.setex("testkey", 10, "52652 哈哈哈");
        System.out.println(jedis.get("testkey"));
    }

}
