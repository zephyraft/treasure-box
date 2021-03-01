package zephyr.proxy;

import zephyr.proxy.dynamic.ProxyHandler;
import zephyr.proxy.statics.HelloImpl;
import zephyr.proxy.statics.HelloInterface;

/**
 * Created by zephyr on 2020/5/21.
 */
public class DynamicProxyDemo {

    public static void main(String[] args) {
        final HelloInterface helloProxy = new ProxyHandler<>(new HelloImpl()).newProxyInstance();
        helloProxy.sayHello();
        helloProxy.sayByeBye("Zephyr");
    }

}
