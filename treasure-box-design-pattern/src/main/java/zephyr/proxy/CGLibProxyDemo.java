package zephyr.proxy;

import zephyr.proxy.cglib.CglibInterceptor;
import zephyr.proxy.cglib.HelloClass;

/**
 * Created by zephyr on 2020/5/21.
 */
public class CGLibProxyDemo {

    public static void main(String[] args) {
        HelloClass helloProxy = new CglibInterceptor<HelloClass>().newProxyInstance(HelloClass.class);
        helloProxy.sayHello();
        helloProxy.sayByeBye("Zephyr");
    }

}
