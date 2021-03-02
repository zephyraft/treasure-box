package zephyr.designpattern.proxy.statics;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HelloImpl implements HelloInterface {
    @Override
    public void sayHello() {
        log.info("Hello Zephyr!");
    }

    @Override
    public String sayByeBye(String userName) {
        log.info("Bye " + userName + "!");
        return "Bye " + userName + "!";
    }
}
