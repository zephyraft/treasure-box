package zephyr.asm.helloworld;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import zephyr.asm.utils.ByteCodeUtils;

import java.io.IOException;
import java.nio.file.Path;

public class AsmHelloWorldDemo {

    public static void main(String[] args) throws IOException {
        byte[] bytes = helloWorldByteCodeGenerator();

        // 写入文件
        Path path = ByteCodeUtils.writeToFile(bytes, "treasure-box-asm/build/classes/java/main/zephyr/asm/helloworld/AsmHelloWorld.class");
        System.out.println(ByteCodeUtils.getPathFullName(path));
        // java zephyr.asm.helloworld.AsmHelloWorld
    }

    // package zephyr.asm.helloworld;
    //
    // public class HelloWorld {
    //     public static void main(String[] var0) {
    //         System.out.println("Hello World");
    //     }
    // }

    // javap -c <class>
    // public class zephyr.asm.helloworld.HelloWorld {
    //   public zephyr.asm.helloworld.HelloWorld();
    //     Code:
    //        0: aload_0
    //        1: invokespecial #1                  // Method java/lang/Object."<init>":()V
    //        4: return
    //
    //   public static void main(java.lang.String[]);
    //     Code:
    //        0: getstatic     #2                  // Field java/lang/System.out:Ljava/io/PrintStream;
    //        3: ldc           #3                  // String Hello World
    //        5: invokevirtual #4                  // Method java/io/PrintStream.println:(Ljava/lang/String;)V
    //        8: return
    // }
    private static byte[] helloWorldByteCodeGenerator() {
        ClassWriter classWriter = new ClassWriter(0);
        // 定义对象头；版本号、修饰符、全类名、签名、父类、实现的接口
        classWriter.visit(Opcodes.V1_8, Opcodes.ACC_PUBLIC,
                "zephyr/asm/helloworld/AsmHelloWorld", null, "java/lang/Object", null);
        // 添加方法；修饰符、方法名、描述符、签名、异常
        MethodVisitor methodVisitor =
                classWriter.visitMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, "main",
                        "([Ljava/lang/String;)V", null, null);
        // 执行指令；获取静态属性
        methodVisitor.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System",
                "out", "Ljava/io/PrintStream;");
        // 加载常量 load constant
        methodVisitor.visitLdcInsn("Hello World");
        // 调用方法
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL,
                "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
        // 返回
        methodVisitor.visitInsn(Opcodes.RETURN);
        // 设置操作数栈的深度和局部变量的大小
        methodVisitor.visitMaxs(2, 1);
        // 方法结束
        methodVisitor.visitEnd();
        // 类完成
        classWriter.visitEnd();
        // 生成字节数组
        return classWriter.toByteArray();
    }
}
