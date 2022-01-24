package zephyraft.classloader;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class CustomClassLoader extends ClassLoader{
    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        try {
            byte[] b = loadClassFromFile(name);
            return defineClass(name, b, 0, b.length);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ClassNotFoundException();
        }
    }

    private byte[] loadClassFromFile(String fileName) throws IOException {
        String name = fileName.replace('.', File.separatorChar) + ".class";
        try (
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(name);
            ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        ) {
            if (inputStream == null) {
                throw new NullPointerException();
            }
            int nextValue;
            while ((nextValue = inputStream.read()) != -1 ) {
                byteStream.write(nextValue);
            }
            return byteStream.toByteArray();
        }
    }
}
