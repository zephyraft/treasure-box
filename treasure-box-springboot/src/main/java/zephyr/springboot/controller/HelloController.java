package zephyr.springboot.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zephyr.springboot.service.HelloService1;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.stream.StreamSupport;

@RestController
@RequiredArgsConstructor
@Slf4j
public class HelloController {

    private final HelloService1 helloService1;

    @GetMapping("/hello")
    public String sayHello() {
        return helloService1.say1();
    }

    @RequestMapping("/**")
    public void all(HttpServletRequest request) throws IOException {
        log.info("url={}", request.getRequestURI());
        request.getHeaderNames().asIterator()
                .forEachRemaining(k -> request.getHeaders(k).asIterator().forEachRemaining(v -> log.info("header {}={}", k, v)));
        request.getParameterNames().asIterator()
                .forEachRemaining(k -> log.info("param {}={}", k, Arrays.toString(request.getParameterValues(k))));
        ServletInputStream inputStream = request.getInputStream();
        byte[] bytes = inputStream.readAllBytes();
        log.info("body={}", new String(bytes, StandardCharsets.UTF_8));
    }

}
