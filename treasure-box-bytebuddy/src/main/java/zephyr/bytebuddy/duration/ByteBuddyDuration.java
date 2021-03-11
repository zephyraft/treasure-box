package zephyr.bytebuddy.duration;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.implementation.bind.annotation.*;
import net.bytebuddy.matcher.ElementMatchers;
import zephyr.bytebuddy.helloworld.HelloWorld;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Paths;
import java.util.concurrent.Callable;

public class ByteBuddyDuration {

    public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, IOException {
        DynamicType.Unloaded<?> dynamicType = new ByteBuddy()
                .subclass(BizClass.class)
                .method(ElementMatchers.named("queryUserInfo"))
                .intercept(MethodDelegation.to(DurationMonitor.class))
                .make();

        dynamicType.saveIn(Paths.get("treasure-box-bytebuddy/build/classes/java/main").toFile());
        Class<?> clazz = dynamicType
                .load(HelloWorld.class.getClassLoader(), ClassLoadingStrategy.Default.INJECTION)
                .getLoaded();

        Object res = clazz
                .getMethod("queryUserInfo", String.class, String.class)
                .invoke(
                        clazz.getConstructor().newInstance(),
                        "10001",
                        "Adhl9dkl"
                );
        System.out.println(res);
    }

    static class DurationMonitor {
        @RuntimeType
        public static Object intercept(@Origin Method method, @AllArguments
                Object[] args, @Argument(0) Object arg0, @SuperCall Callable<?> callable) throws Exception {
            long start = System.currentTimeMillis();
            Object res = null;
            try {
                res = callable.call();
                return res;
            } finally {
                System.out.println("方法名称：" + method.getName());
                System.out.println("入参个数：" + method.getParameterCount());
                System.out.println("入参类型：" + method.getParameterTypes()[0].getTypeName() + "、" + method.getParameterTypes()[1].getTypeName());
                System.out.println("入参内容：" + arg0 + "、" + args[1]);
                System.out.println("出参类型：" + method.getReturnType().getName());
                System.out.println("出参结果：" + res);
                System.out.println("方法耗时：" + (System.currentTimeMillis() - start) + "ms");
            }
        }
    }
}
