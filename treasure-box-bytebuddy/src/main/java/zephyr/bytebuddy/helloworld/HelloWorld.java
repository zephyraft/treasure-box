package zephyr.bytebuddy.helloworld;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.implementation.MethodDelegation;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.nio.file.Paths;

import static net.bytebuddy.matcher.ElementMatchers.named;

public class HelloWorld {

    public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, IOException, ClassNotFoundException {
        DynamicType.Unloaded<Object> dynamicType = new ByteBuddy()
                .subclass(Object.class)
                .name("zephyr.bytebuddy.helloworld.BytebuddyHelloWorld")
                .method(named("toString"))
                .intercept(FixedValue.value("Hello World!"))
                .defineMethod("main", void.class, Modifier.PUBLIC + Modifier.STATIC)
                .withParameter(String[].class, "args")
                .intercept(MethodDelegation.to(Hi.class))
                .make();

        dynamicType.saveIn(Paths.get("treasure-box-bytebuddy/build/classes/java/main").toFile());

        Class<?> clazz = dynamicType
                .load(HelloWorld.class.getClassLoader(), ClassLoadingStrategy.Default.INJECTION)
                .getLoaded();

        Object byteBuddyHelloWorld = clazz
                .getConstructor()
                .newInstance();

        clazz.getMethod("main", String[].class).invoke(byteBuddyHelloWorld, (Object) new String[0]);
        System.out.println(byteBuddyHelloWorld.toString());
    }

    static class Hi {

        public static void main(String[] args) {
            System.out.println("Hello!");
        }

    }
}
