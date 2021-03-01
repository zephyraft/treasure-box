package zephyr.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("helloService1")
public class HelloService1 {

    @Autowired
    private HelloService2 helloService2;

    public String say1() {
        System.out.println(helloService2.toString());
        return helloService2.say2();
    }

}
