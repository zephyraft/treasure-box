package zephyr.proxy.dynamic;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by zephyr on 2020/5/21.
 */
@Slf4j
public class ProxyHandler<T> implements InvocationHandler {

    private final T object;

    public ProxyHandler(T object) {
        this.object = object;
    }

    /**
     * 动态生成代理类对象,Proxy.newProxyInstance
     * @return 返回代理类的实例
     */
    @SuppressWarnings("unchecked")
    public T newProxyInstance() {
        return (T) Proxy.newProxyInstance(
                //指定代理对象的类加载器
                object.getClass().getClassLoader(),
                //代理对象需要实现的接口，可以同时指定多个接口
                object.getClass().getInterfaces(),
                //方法调用的实际处理者，代理对象的方法调用都会转发到这里
                this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        log.info("Before invoke " + method.getName());
        final Object returnObject = method.invoke(object, args);
        log.info("After invoke " + method.getName());
        return returnObject;
    }
}
