package zephyr.opentelemetry.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@Slf4j
public class TestController {

    @GetMapping("/")
    public Mono<String> helloOtel() {
        log.info("test log 123");
        return Mono.just("hello otel");
    }

}
