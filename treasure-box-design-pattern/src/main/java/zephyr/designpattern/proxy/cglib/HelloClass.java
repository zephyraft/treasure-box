package zephyr.designpattern.proxy.cglib;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HelloClass {
    public void sayHello() {
        log.info("Hello Zephyr!");
    }

    public String sayByeBye(String userName) {
        log.info("Bye " + userName + "!");
        return "Bye " + userName + "!";
    }
}
