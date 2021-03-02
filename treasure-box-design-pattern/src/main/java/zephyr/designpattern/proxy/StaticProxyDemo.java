package zephyr.designpattern.proxy;

import zephyr.designpattern.proxy.statics.HelloInterface;
import zephyr.designpattern.proxy.statics.HelloProxy;

/**
 * 使用静态代理很容易就完成了对一个类的代理操作。但是静态代理的缺点也暴露了出来：由于代理只能为一个类服务，如果需要代理的类很多，那么就需要编写大量的代理类，比较繁琐。
 */
public class StaticProxyDemo {

    public static void main(String[] args) {
        final HelloInterface helloProxy = new HelloProxy();
        helloProxy.sayHello();
        helloProxy.sayByeBye("Zephyr");
    }

}
