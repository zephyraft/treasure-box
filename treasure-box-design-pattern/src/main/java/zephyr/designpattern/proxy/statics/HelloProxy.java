package zephyr.designpattern.proxy.statics;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HelloProxy implements HelloInterface {

    private final HelloInterface hello = new HelloImpl();

    @Override
    public void sayHello() {
        log.info("Before invoke sayHello");
        hello.sayHello();
        log.info("After invoke sayHello");
    }

    @Override
    public String sayByeBye(String userName) {
        log.info("Before invoke sayByeBye");
        final String s = hello.sayByeBye(userName);
        log.info("After invoke sayByeBye");
        return s;
    }
}
