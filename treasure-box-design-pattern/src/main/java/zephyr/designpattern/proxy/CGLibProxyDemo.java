package zephyr.designpattern.proxy;

import zephyr.designpattern.proxy.cglib.CglibInterceptor;
import zephyr.designpattern.proxy.cglib.HelloClass;

public class CGLibProxyDemo {

    public static void main(String[] args) {
        HelloClass helloProxy = new CglibInterceptor<HelloClass>().newProxyInstance(HelloClass.class);
        helloProxy.sayHello();
        helloProxy.sayByeBye("Zephyr");
    }

}
