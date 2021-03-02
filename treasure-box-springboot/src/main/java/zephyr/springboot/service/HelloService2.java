package zephyr.springboot.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("helloService2")
public class HelloService2 {

    @Autowired
    private HelloService1 helloService1;

    public String say2() {
        System.out.println(helloService1.toString());
        return "helloService2 say hello";
    }

}
