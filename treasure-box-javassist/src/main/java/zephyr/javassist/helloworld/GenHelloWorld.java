package zephyr.javassist.helloworld;

import javassist.*;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class GenHelloWorld {

    public static void main(String[] args) throws IOException,
            CannotCompileException, NotFoundException, IllegalAccessException,
            InstantiationException, NoSuchMethodException, InvocationTargetException {
        ClassPool pool = ClassPool.getDefault();
        // 创建类 classname：创建类路径和名称
        CtClass ctClass = pool.makeClass("zephyr.javassist.helloworld.JavassistHelloWorld");
        // 添加方法
        CtMethod mainMethod = new CtMethod(CtClass.voidType, "main", new CtClass[]{pool.get(String[].class.getName())}, ctClass);
        mainMethod.setModifiers(Modifier.PUBLIC + Modifier.STATIC);
        mainMethod.setBody("{System.out.println(\"javassist helloworld\");}");
        ctClass.addMethod(mainMethod);
        // 创建无参数构造方法
        CtConstructor ctConstructor = new CtConstructor(new CtClass[]{}, ctClass);
        ctConstructor.setBody("{}");
        ctClass.addConstructor(ctConstructor);
        // 输出类内容
        ctClass.writeFile("treasure-box-javassist/build/classes/java/main");
        // 测试调用
        Class<?> clazz = ctClass.toClass();
        Method main = clazz.getDeclaredMethod("main", String[].class);
        main.invoke(clazz.getConstructor().newInstance(), (Object) new String[1]);
    }

}
