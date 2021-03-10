package zephyr.asm.utils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

public class ByteCodeUtils {

    private ByteCodeUtils() {
    }

    /**
     * 字节码写入文件
     * @param bytes 字节码
     * @param filePath 文件路径
     */
    public static Path writeToFile(byte[] bytes, String filePath) throws IOException {
        Path path = Paths.get(filePath);
        return Files.write(path, bytes);
    }

    /**
     * 字节码文件加载类
     * @param path 文件路径
     * @param className 类名
     */
    public static Class<?> loadClass(Path path, String className) throws ClassNotFoundException, MalformedURLException {
        URLClassLoader urlClassLoader = new URLClassLoader(new URL[]{path.toUri().toURL()});
        return urlClassLoader.loadClass(className);
    }

    /**
     * 直接从byte数组加载类
     * @param bytes 字节码
     * @param className 类名
     */
    public static Class<?> defineClass(byte[] bytes, String className) throws ClassNotFoundException {
        ByteClassLoader byteClassLoader = new ByteClassLoader(new URL[]{}, ByteCodeUtils.class.getClassLoader(), Map.of(className, bytes));
        return byteClassLoader.findClass(className);
    }

    public static String getPathFullName(Path path) {
        if (!Files.exists(path)) {
            return path.toString();
        }

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < path.getNameCount(); i++) {
            stringBuilder.append(path.getName(i).toString());
            if (i != path.getNameCount() - 1) {
                stringBuilder.append("/");
            }
        }
        return stringBuilder.toString();
    }
}
