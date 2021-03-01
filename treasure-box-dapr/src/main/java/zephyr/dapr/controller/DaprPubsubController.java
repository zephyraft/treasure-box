package zephyr.dapr.controller;

import io.dapr.Topic;
import io.dapr.client.DaprClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Map;

@RestController
@Slf4j
public class DaprPubsubController {

    private final DaprClient daprClient;

    public DaprPubsubController(DaprClient daprClient) {
        this.daprClient = daprClient;
    }

    @GetMapping("/pub")
    public Mono<Void> pub() {
        String message = "dapr你好!";
        log.info("Published message: {}", message);
        return daprClient.publishEvent("pubsub", "dapr_test", message.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * PostMapping需要和topic name一致
     */
    @Topic(name = "dapr_test", pubsubName = "pubsub")
    @PostMapping("/dapr_test")
    public Mono<Void> sub(@RequestBody(required = false) byte[] body,
                          @RequestHeader Map<String, String> headers) {
        return Mono.fromRunnable(() -> {
            try {
                String message = new String(body, StandardCharsets.UTF_8);
                log.info("Subscriber got message: {}", message);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }
}
