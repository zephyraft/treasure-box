package zephyr.javassist.math;

import javassist.*;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class GenHelloMath {

    public static void main(String[] args) throws IOException,
            CannotCompileException, NotFoundException, IllegalAccessException,
            InstantiationException, NoSuchMethodException, InvocationTargetException {
        ClassPool pool = ClassPool.getDefault();
        // 创建类 classname：创建类路径和名称
        CtClass ctClass = pool.makeClass("zephyr.javassist.math.JavassistHelloMath");

        // 属性字段
        CtField ctField = new CtField(CtClass.doubleType, "PI", ctClass);
        ctField.setModifiers(Modifier.PRIVATE + Modifier.STATIC + Modifier.FINAL);
        ctClass.addField(ctField, "3.14159");

        // 添加方法
        CtMethod piMethod = new CtMethod(CtClass.doubleType, "calculateCircularArea", new CtClass[]{CtClass.doubleType}, ctClass);
        piMethod.setModifiers(Modifier.PUBLIC);
        piMethod.setBody("{return PI * $1 * $1;}");
        ctClass.addMethod(piMethod);

        // 添加方法
        CtMethod sumMethod = new CtMethod(CtClass.doubleType, "sumOfTwoNumbers", new CtClass[]{CtClass.doubleType, CtClass.doubleType}, ctClass);
        sumMethod.setModifiers(Modifier.PUBLIC);
        sumMethod.setBody("{return $1 + $2;}");
        ctClass.addMethod(sumMethod);

        // 输出类内容
        ctClass.writeFile("treasure-box-javassist/build/classes/java/main");

        // 测试调用
        Class<?> clazz = ctClass.toClass();
        Object obj = clazz.getConstructor().newInstance();
        Method methodCircularArea =
        clazz.getDeclaredMethod("calculateCircularArea", double.class);
        System.out.println("圆面积：" + methodCircularArea.invoke(obj, 1.23));
        Method methodSum = clazz.getDeclaredMethod("sumOfTwoNumbers",
        double.class, double.class);
        System.out.println("两数和：" + methodSum.invoke(obj, 1, 2));
    }

}
