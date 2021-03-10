package zephyr.asm.monitor;

import org.objectweb.asm.*;
import org.objectweb.asm.commons.AdviceAdapter;
import zephyr.asm.utils.ByteCodeUtils;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 在原有方法上字节码增强监控耗时
 */
public class UserServiceMonitor {

    public static void main(String[] args) throws IOException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        byte[] bytes = monitorGenerator();
        ByteCodeUtils.writeToFile(bytes, "treasure-box-asm/build/classes/java/main/zephyr/asm/monitor/AsmUserService.class");
        Class<?> clazz = ByteCodeUtils.defineClass(bytes, "zephyr.asm.monitor.UserService");
        Method queryUserInfo = clazz.getMethod("queryUserInfo", String.class);
        Object obj = queryUserInfo.invoke(clazz.getConstructor().newInstance(), "10001");
        System.out.println("测试结果：" + obj);
    }

    // package zephyr.asm.monitor;
    //
    // public class UserService {
    //
    //     public String queryUserInfo(String uid) {
    //         System.out.println("xxxx");
    //         System.out.println("xxxx");
    //         System.out.println("xxxx");
    //         System.out.println("xxxx");
    //         return uid;
    //     }
    //
    // }
    private static byte[] monitorGenerator() throws IOException {
        // 读取原有类
        ClassReader cr = new ClassReader(UserService.class.getName());
        ClassWriter cw = new ClassWriter(cr, ClassWriter.COMPUTE_MAXS);
        {
            MethodVisitor methodVisitor =
                    cw.visitMethod(Opcodes.ACC_PUBLIC, "<init>", "()V", null, null);
            methodVisitor.visitVarInsn(Opcodes.ALOAD, 0);
            methodVisitor.visitMethodInsn(Opcodes.INVOKESPECIAL,
                    "java/lang/Object", "<init>", "()V", false);
            methodVisitor.visitInsn(Opcodes.RETURN);
            methodVisitor.visitMaxs(1, 1);
            methodVisitor.visitEnd();
        }

        // 增强字节码
        ClassVisitor cv = new ProfilingClassAdapter(cw, UserService.class.getSimpleName());
        cr.accept(cv, ClassReader.EXPAND_FRAMES);
        return cw.toByteArray();
    }

    static class ProfilingClassAdapter extends ClassVisitor {
        public ProfilingClassAdapter(final ClassVisitor cv, String innerClassName) {
            super(Opcodes.ASM9, cv);
        }

        public MethodVisitor visitMethod(int access,
                                         String name,
                                         String desc,
                                         String signature,
                                         String[] exceptions) {
//            System.out.println("access：" + access);
//            System.out.println("name：" + name);
//            System.out.println("desc：" + desc);
            if (!"queryUserInfo".equals(name)) return null;
            MethodVisitor mv = cv.visitMethod(access, name, desc,
                    signature, exceptions);
            return new ProfilingMethodVisitor(mv, access, name, desc);
        }
    }

    static class ProfilingMethodVisitor extends AdviceAdapter {
        private final String methodName;

        protected ProfilingMethodVisitor(MethodVisitor methodVisitor, int
                access, String name, String descriptor) {
            super(Opcodes.ASM9, methodVisitor, access, name, descriptor);
            this.methodName = name;
        }

        // 方法进入时
        @Override
        protected void onMethodEnter() {
            // 打印参数
            mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out",
                    "Ljava/io/PrintStream;");
            mv.visitVarInsn(ALOAD, 1);
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println",
                    "(Ljava/lang/String;)V", false);

            // 开始计时
            mv.visitMethodInsn(INVOKESTATIC, "java/lang/System",
                    "nanoTime", "()J", false);
            mv.visitVarInsn(LSTORE, 2);

            // 调用外部类
            mv.visitLdcInsn(methodName);
            mv.visitInsn(ICONST_1);
            mv.visitIntInsn(NEWARRAY, T_INT);
            mv.visitInsn(DUP);
            mv.visitInsn(ICONST_0);
            mv.visitVarInsn(ALOAD, 1);
            mv.visitMethodInsn(INVOKESTATIC, "java/lang/Integer", "parseInt", "(Ljava/lang/String;)I", false);
            mv.visitInsn(IASTORE);
            mv.visitMethodInsn(INVOKESTATIC, "zephyr/asm/monitor/MonitorLog", "info", "(Ljava/lang/String;[I)V", false);
        }

        // 方法退出时
        @Override
        protected void onMethodExit(int opcode) {
            // 结束计时并输出结果
            if ((IRETURN <= opcode && opcode <= RETURN) || opcode ==
                    ATHROW) {
                mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out",
                        "Ljava/io/PrintStream;");
                mv.visitTypeInsn(NEW, "java/lang/StringBuilder");
                mv.visitInsn(DUP);
                mv.visitMethodInsn(INVOKESPECIAL,
                        "java/lang/StringBuilder", "<init>", "()V", false);
                mv.visitLdcInsn("方法执行耗时(纳秒)->" + methodName + "：");
                mv.visitMethodInsn(INVOKEVIRTUAL,
                        "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
                mv.visitMethodInsn(INVOKESTATIC, "java/lang/System",
                        "nanoTime", "()J", false);
                mv.visitVarInsn(LLOAD, 2);
                mv.visitInsn(LSUB);
                mv.visitMethodInsn(INVOKEVIRTUAL,
                        "java/lang/StringBuilder", "append", "(J)Ljava/lang/StringBuilder;",
                        false);
                mv.visitMethodInsn(INVOKEVIRTUAL,
                        "java/lang/StringBuilder", "toString", "()Ljava/lang/String;", false);
                mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream",
                        "println", "(Ljava/lang/String;)V", false);
            }
        }
    }
}
