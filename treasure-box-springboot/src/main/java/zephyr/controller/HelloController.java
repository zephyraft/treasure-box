package zephyr.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import zephyr.service.HelloService1;

@RestController
@RequiredArgsConstructor
public class HelloController {

    private final HelloService1 helloService1;

    @GetMapping("/hello")
    public String sayHello() {
        return helloService1.say1();
    }
}
