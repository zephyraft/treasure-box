package zephyr.designpattern.proxy;

import zephyr.designpattern.proxy.dynamic.ProxyHandler;
import zephyr.designpattern.proxy.statics.HelloImpl;
import zephyr.designpattern.proxy.statics.HelloInterface;

public class DynamicProxyDemo {

    public static void main(String[] args) {
        final HelloInterface helloProxy = new ProxyHandler<>(new HelloImpl()).newProxyInstance();
        helloProxy.sayHello();
        helloProxy.sayByeBye("Zephyr");
    }

}
