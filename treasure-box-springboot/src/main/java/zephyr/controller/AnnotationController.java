package zephyr.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import zephyr.annotation.ProfileAnnotationDemo;

/**
 * Created by zephyr on 2020/3/30.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/annotation")
public class AnnotationController {

    private final ProfileAnnotationDemo.ProfileAnnotationBean profileAnnotationBean;

    @GetMapping("/profile")
    public Mono<String> profile() {
        return Mono.just(profileAnnotationBean.getValue());
    }


}
