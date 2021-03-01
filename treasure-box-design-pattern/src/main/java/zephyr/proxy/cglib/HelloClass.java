package zephyr.proxy.cglib;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by zephyr on 2020/5/21.
 */
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
