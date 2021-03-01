package zephyr.dapr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * io.dapr.springboot需要包括
 */
@SpringBootApplication(scanBasePackages = {"io.dapr.springboot", "zephyr.dapr"})
public class DaprDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DaprDemoApplication.class, args);
    }

}
