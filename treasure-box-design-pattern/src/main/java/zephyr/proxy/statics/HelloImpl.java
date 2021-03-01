package zephyr.proxy.statics;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by zephyr on 2020/5/21.
 */
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
