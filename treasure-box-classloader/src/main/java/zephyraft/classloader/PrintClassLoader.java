package zephyraft.classloader;

// https://zhuanlan.zhihu.com/p/51374915
// https://www.baeldung.com/java-classloaders
public class PrintClassLoader {
    public void printClassLoaders() {
        // AppClassLoader
        // 加载一般的应用类
        System.out.println("Classloader of this class:"
                + PrintClassLoader.class.getClassLoader());

        // jdk9+ PlatformClassLoader 代替 ExtClassLoader
        // 加载 jdk 非核心类 通常javax.开头
        System.out.println("Classloader of Logging:"
                + javax.sql.DataSource.class.getClassLoader());

        // Bootstrap ClassLoader, get为null(c实现，没有对应java类)
        // 加载 jdk 核心类 通常以java.开头
        System.out.println("Classloader of ArrayList:"
                + java.util.ArrayList.class.getClassLoader());
    }

    public static void main(String[] args) throws ClassNotFoundException {
        new PrintClassLoader().printClassLoaders();
        System.out.println(PrintClassLoader.class.getClassLoader());
        Class<?> classA = new CustomClassLoader().findClass("zephyraft.classloader.Test");
        Class<?> classB = PrintClassLoader.class.getClassLoader().loadClass("zephyraft.classloader.Test");
        Class<?> classC = PrintClassLoader.class.getClassLoader().loadClass("zephyraft.classloader.Test");
        Class<?> classD = new CustomClassLoader().findClass("zephyraft.classloader.Test");
        Class<?> classE = new CustomClassLoader().findClass("java.lang.String");
        System.out.println(classA);
        System.out.println(classB);
        System.out.println(classC);
        System.out.println(classD);
        System.out.println(classA == classB);
        System.out.println(classB == classC);
        System.out.println(classA == classD);
        System.out.println(classE);
    }
}
