package zephyr.springboot.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zephyr.springboot.annotation.ProfileAnnotationDemo;


@RestController
@RequiredArgsConstructor
@RequestMapping("/annotation")
public class AnnotationController {

    private final ProfileAnnotationDemo.ProfileAnnotationBean profileAnnotationBean;

    @GetMapping("/profile")
    public String profile() {
        return profileAnnotationBean.getValue();
    }


}
