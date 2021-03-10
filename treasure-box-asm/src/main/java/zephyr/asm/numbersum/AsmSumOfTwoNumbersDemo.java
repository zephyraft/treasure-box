package zephyr.asm.numbersum;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import zephyr.asm.utils.ByteCodeUtils;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Path;

public class AsmSumOfTwoNumbersDemo {

    public static void main(String[] args) throws IOException, NoSuchMethodException, ClassNotFoundException, IllegalAccessException, InvocationTargetException, InstantiationException {
        byte[] bytes = sumOfTwoNumbersByteCodeGenerator();
        // 写入文件
        Path path = ByteCodeUtils.writeToFile(bytes, "treasure-box-asm/build/classes/java/main/zephyr/asm/numbersum/AsmSumOfTwoNumbers.class");
        // 加载类
        Class<?> clazz = ByteCodeUtils.loadClass(path, "zephyr.asm.numbersum.AsmSumOfTwoNumbers");
        // 反射创建实例
        Object instance = clazz.getConstructor().newInstance();
        // 反射获取 sum 方法
        Method method = clazz.getMethod("sum", int.class, int.class);
        Object obj = method.invoke(instance, 7, 2);
        System.out.println(obj);
    }

    // package zephyr.asm.numbersum;
    //
    // public class SumOfTwoNumbers {
    //
    //     public int sum(int i, int m) {
    //         return i + m;
    //     }
    //
    // }
    private static byte[] sumOfTwoNumbersByteCodeGenerator() {
        ClassWriter classWriter = new ClassWriter(0);
        {
            MethodVisitor methodVisitor =
                    classWriter.visitMethod(Opcodes.ACC_PUBLIC, "<init>", "()V", null, null);
            methodVisitor.visitVarInsn(Opcodes.ALOAD, 0);
            methodVisitor.visitMethodInsn(Opcodes.INVOKESPECIAL,
                    "java/lang/Object", "<init>", "()V", false);
            methodVisitor.visitInsn(Opcodes.RETURN);
            methodVisitor.visitMaxs(1, 1);
            methodVisitor.visitEnd();
        }
        {
            // 定义对象头；版本号、修饰符、全类名、签名、父类、实现的接口
            classWriter.visit(Opcodes.V1_8, Opcodes.ACC_PUBLIC,
                    "zephyr/asm/numbersum/AsmSumOfTwoNumbers", null, "java/lang/Object", null);
            // 添加方法；修饰符、方法名、描述符、签名、异常
            MethodVisitor methodVisitor =
                    classWriter.visitMethod(Opcodes.ACC_PUBLIC, "sum", "(II)I", null, null);
            methodVisitor.visitVarInsn(Opcodes.ILOAD, 1);
            methodVisitor.visitVarInsn(Opcodes.ILOAD, 2);
            methodVisitor.visitInsn(Opcodes.IADD);
            // 返回
            methodVisitor.visitInsn(Opcodes.IRETURN);
            // 设置操作数栈的深度和局部变量的大小
            methodVisitor.visitMaxs(2, 3);
            methodVisitor.visitEnd();
        }
        // 类完成
        classWriter.visitEnd();
        // 生成字节数组
        return classWriter.toByteArray();
    }
}
