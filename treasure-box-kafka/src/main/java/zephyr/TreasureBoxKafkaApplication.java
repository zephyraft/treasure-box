package zephyr;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.core.KafkaTemplate;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.concurrent.CountDownLatch;

/**
 * Created by zephyr on 2020/6/5.
 */
@Slf4j
@SpringBootApplication
@RequiredArgsConstructor
public class TreasureBoxKafkaApplication implements CommandLineRunner {

    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final SecureRandom random = new SecureRandom();

    private final CountDownLatch latch = new CountDownLatch(3);

    public static void main(String[] args) {
        SpringApplication.run(TreasureBoxKafkaApplication.class, args);
    }

//    @Override
//    public void run(String... args) throws Exception {
//        this.kafkaTemplate.send("myTopic", "foo1");
//        this.kafkaTemplate.send("myTopic", "foo2");
//        this.kafkaTemplate.send("myTopic", "foo3");
//        latch.await(60, TimeUnit.SECONDS);
//        log.info("All received");
//    }
//
//    @KafkaListener(topics = "myTopic")
//    public void listen(ConsumerRecord<?, ?> cr) throws Exception {
//        log.info(cr.toString());
//        latch.countDown();
//    }

    @Override
    public void run(String... args) throws Exception {
        new Thread(() -> {
            while (true) {
                Metrics metrics = new Metrics();
                metrics.setHost("127.0.0.1");
                metrics.setMetricName("m1");
                metrics.setTimestamp(LocalDateTime.now());
                metrics.setValue(String.valueOf(random.nextInt(100)));

                try {
                    final String s = objectMapper.writeValueAsString(metrics);
                    this.kafkaTemplate.send("test", s);
                    log.info("send msg => " + s);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }

                try {
                    Thread.sleep(random.nextInt(5) * 1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(() -> {
            while (true) {
                Metrics metrics = new Metrics();
                metrics.setHost("127.0.0.1");
                metrics.setMetricName("m2");
                metrics.setTimestamp(LocalDateTime.now());
                metrics.setValue(String.valueOf(random.nextInt(100)));

                try {
                    final String s = objectMapper.writeValueAsString(metrics);
                    this.kafkaTemplate.send("test", s);
                    log.info("send msg => " + s);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }

                try {
                    Thread.sleep(random.nextInt(5) * 1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public class Metrics {

        private String host;
        private String metricName;
        private String value;
        private LocalDateTime timestamp;

        public String getHost() {
            return host;
        }

        public void setHost(String host) {
            this.host = host;
        }

        public String getMetricName() {
            return metricName;
        }

        public void setMetricName(String metricName) {
            this.metricName = metricName;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public LocalDateTime getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(LocalDateTime timestamp) {
            this.timestamp = timestamp;
        }
    }
}
