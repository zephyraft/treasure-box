package zephyr.jvm;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;


@Slf4j
public class ClassLoaderTest {

    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException {

        ClassLoader myLoader = new ClassLoader() {
            // 重写loadClass 破坏了双亲委派模型
            @Override
            public Class<?> loadClass(String name) throws ClassNotFoundException {
                try {
                    final String fileName = name.substring(name.lastIndexOf(".") + 1) + ".class";
                    final InputStream is = this.getClass().getResourceAsStream(fileName);
                    if (is == null) {
                        return super.loadClass(name);
                    }
                    byte[] b = new byte[is.available()];
                    is.read(b);
                    return defineClass(name, b, 0, b.length);
                } catch (IOException e) {
                    throw new ClassNotFoundException(name);
                }
            }
        };

        final Object obj = myLoader.loadClass("zephyr.jvm.ClassLoaderTest").newInstance();
        log.info("{}", obj.getClass());
        log.info("{}", obj instanceof ClassLoaderTest);

    }

}
