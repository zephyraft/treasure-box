package zephyr.lombok;

import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import zephyr.lombok.model.*;
import zephyr.lombok.nonnull.NonNullExample;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;


@Slf4j
public class LombokDemo {

    public static void main(String[] args) {
        // @Log (实用)
        log.info("lombok @slf4j, see https://projectlombok.org/features/log from more");

        // @Val (可用)
        val example = new ArrayList<String>();
        example.add("Hello, World!");
        log.info(example.toString());

        // @NonNull (感觉用处不大)
        NonNullExample nonNullExample = new NonNullExample(new Student());
        log.info(nonNullExample.getName());

        // @Cleanup (try with resource)
        try {
            cleanup();
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }

        // @Getter/@Setter (实用)
        Student student = new Student();
        student.setAge(1);
        student.setName("1");

        // @ToString (可用)
        log.info(student.toString());

        // @EqualsAndHashCode (可用)
        log.info("{}", student.hashCode());

        // @NoArgsConstructor, @RequiredArgsConstructor and @AllArgsConstructor (实用)
        Student studentConstructorTest = new Student(1);
        log.info(studentConstructorTest.toString());

        // @Data（整合@ToString，@EqualsAndHashCode， @Getter，所有非final字段的@Setter，以及 @RequiredArgsConstructor！）
        DataModel dataModel = new DataModel("中文123214");
        log.info(dataModel.toString());

        // @Value (@Data的不可变体)
        ValueExample valueExample = new ValueExample(new String[]{"123", "31"}, "123", 1D, 1);
        log.info(valueExample.toString());

        // @Builder 创建对象
        ValueBuilderExample.ValueBuilderExampleBuilder builder = ValueBuilderExample.builder();
        ValueBuilderExample build = builder.build();
        log.info(build.toString());

        // @SneakyThrows 偷偷抛出已检查的异常而不在方法的throws子句中实际声明这一点 应该谨慎使用
        SneakyThrowsExample sneakyThrowsExample = new SneakyThrowsExample();
        log.info(sneakyThrowsExample.utf8ToString("123aqwrt".getBytes()));

        // @Synchronized 是synchronized方法修饰符的更安全的变体
        SynchronizedExample synchronizedExample = new SynchronizedExample();
        synchronizedExample.foo();

        GetterLazyExample getterLazyExample = new GetterLazyExample();
        double[] cached = getterLazyExample.getCached();
        log.info("{}", cached[0]);
    }

    private static void cleanup() throws IOException {
        @Cleanup InputStream in = new ByteArrayInputStream("123aqwrt".getBytes());
        @Cleanup ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] b = new byte[10000];
        while (true) {
            int r = in.read(b);
            if (r == -1) break;
            out.write(b, 0, r);
        }
        log.info(out.toString());
    }

}
