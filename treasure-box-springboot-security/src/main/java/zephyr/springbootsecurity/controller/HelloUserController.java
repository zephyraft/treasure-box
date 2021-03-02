package zephyr.springbootsecurity.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import zephyr.springbootsecurity.service.HelloWorldMessageService;

import java.security.Principal;

@RestController
public class HelloUserController {

    public static final Logger log = LoggerFactory.getLogger(HelloUserController.class);
    private final HelloWorldMessageService messages;

    public HelloUserController(HelloWorldMessageService messages) {
        this.messages = messages;
    }

    @GetMapping("/")
    public Mono<String> hello(Mono<Principal> principal) {
        return this.messages.findMessage(principal);
    }

}
