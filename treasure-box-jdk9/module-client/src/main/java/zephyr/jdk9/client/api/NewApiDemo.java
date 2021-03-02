package zephyr.jdk9.client.api;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class NewApiDemo {

    public static void main(String[] args) throws IOException, InterruptedException {
        // 增加了List.of()、Set.of()、Map.of() 和 Map.ofEntries()等工厂方法来创建不可变集合
        System.out.println(List.of());
        System.out.println(List.of("Hello", "World"));
        System.out.println(List.of(1, 2, 3));
        System.out.println(Set.of());
        System.out.println(Set.of("Hello", "World"));
        System.out.println(Set.of(1, 2, 3));
        System.out.println(Map.of());
        System.out.println(Map.of("Hello", 1, "World", 2));

        // Stream 中增加了新的方法 ofNullable、dropWhile、takeWhile 和 iterate。
        Stream.ofNullable(null).forEach(System.out::println);
        Stream.of(1, 2, 3, 4, 5)
                .dropWhile(i -> i % 2 != 0)
                .forEach(System.out::println);
        Stream.of(1, 2, 3, 4, 5)
                .takeWhile(i -> i % 2 != 0)
                .forEach(System.out::println);
        Stream.iterate(0, n -> n + 2).limit(5).forEach(System.out::println); // 返回无限流

        // Collectors 中增加了新的方法 filtering 和 flatMapping
        Stream.of("a", "ab", "abc")
                .collect(Collectors.flatMapping(v -> v.chars().boxed(), // flatMapping 把 String 映射成 Integer 流 ，再把所有的 Integer 收集到一个集合中
                        Collectors.toSet()))
                .forEach(System.out::println);

        // Optional新增了 ifPresentOrElse、or 和 stream 等方法
        Stream.of(
                Optional.of(1),
                Optional.empty(),
                Optional.of(2)
        ).flatMap(Optional::stream)
                .forEach(System.out::println);

        // 进程
        final ProcessBuilder processBuilder = new ProcessBuilder("top")
                .inheritIO();
        final ProcessHandle processHandle = processBuilder.start().toHandle();
        processHandle.onExit().whenCompleteAsync((handle, throwable) -> {
            if (throwable == null) {
                System.out.println(handle.pid());
            } else {
                throwable.printStackTrace();
            }
        });

    }

}
