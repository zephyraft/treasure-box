package zephyr.demo;

import lombok.extern.slf4j.Slf4j;
import zephyr.model.Dish;
import zephyr.model.ModelFactory;
import zephyr.model.Transaction;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Created by zephyr on 2018/10/16.
 */
@Slf4j
public class StreamAPIDemo {

    public static void main(String[] args) {
        List<Dish> menu = ModelFactory.getMenu();
        List<Transaction> transactions = ModelFactory.getTransaction();

        // 第一个示例
        log.info("{}", menu
                // 从menu获得流
                .stream()
                // == 中间操作 == （filter， map循环合并；limit短路）
                // 从流中排除某些元素
                .filter(d -> d.getCalories() > 300)
                // 排序
                .sorted(Comparator.comparingInt(Dish::getCalories))
                // 将元素转换成其他形式或提取信息
                .map(Dish::getName)
                // 截断流
                .limit(2)
                // == 终端操作 ==
                // 把流归约成一个集合
                .collect(Collectors.toList()));

        // forEach终端操作
        menu.forEach(d -> log.info("{}", d));

        // count终端操作
        log.info("{}", menu.stream()
                .filter(d -> d.getCalories() > 300)
                // 去重
                .distinct()
                .limit(3)
                // 返回流中元素的个数
                .count());

        // 流的使用一般包括三件事
        // 一个数据源（如集合）来执行一个查询；
        // 一个中间操作链，形成一条流的流水线；
        // 一个终端操作，执行流水线，并能生成结果。
        // 流的流水线背后的理念类似于构建器模式。在构建器模式中有一个调用链用来设置一套配
        // 置（对流来说这就是一个中间操作链），接着是调用built方法（对流来说就是终端操作）。

        // 用谓词筛选
        log.info("{}", menu.stream()
                .filter(Dish::isVegetarian)
                .collect(Collectors.toList()));
        // 筛选各异的元素
        Stream.of(1, 2, 1, 3, 3, 2, 4)
                .filter(i -> i % 2 == 0)
                .distinct()
                .forEach(d -> log.info("{}", d));
        // 截短流
        log.info("{}", menu.stream()
                .filter(d -> d.getCalories() > 300)
                .limit(3)
                .collect(Collectors.toList()));
        // 跳过元素
        log.info("{}", menu.stream()
                .filter(d -> d.getCalories() > 300)
                .skip(2)
                .collect(Collectors.toList()));

        // 映射
        log.info("{}", menu.stream()
                .map(Dish::getName)
                .collect(Collectors.toList()));
        log.info("{}", Stream.of("Java 8", "Lambdas", "In", "Action")
                .map(String::length)
                .collect(Collectors.toList()));
        // 流的扁平化
        log.info("{}", Stream.of("Hello", "World")
                //将每个单词转换为由其字母构成的数组
                .map(w -> w.split(""))
                //将各个生成流扁平化为单个流
                .flatMap(Arrays::stream)
                .distinct()
                .collect(Collectors.toList()));

        // 检查谓词是否至少匹配一个元素
        log.info("{}", menu.stream()
                .anyMatch(Dish::isVegetarian));
        // 检查谓词是否匹配所有元素
        log.info("{}", menu.stream()
                .allMatch(d -> d.getCalories() < 1000));
        log.info("{}", menu.stream()
                .noneMatch(d -> d.getCalories() >= 1000));
        // 查找一个任意元素
        log.info("{}", menu.stream()
                .filter(Dish::isVegetarian)
                .findAny());
        // 查找第一个元素
        log.info("{}", Stream.of(1, 2, 3, 4, 5)
                .map(x -> x * x)
                .filter(x -> x % 3 == 0)
                .findFirst());

        // 元素求和
        log.info("{}", Stream.of(4, 5, 3, 9).reduce(Integer::sum));
        // 最大值
        log.info("{}", Stream.of(4, 5, 3, 9).reduce(Integer::max));

        // 原始类型流特化
        log.info("{}", menu.stream()
                .mapToInt(Dish::getCalories)
                .sum());
        // 转换回对象流
        Stream<Integer> stream = menu.stream().mapToInt(Dish::getCalories).boxed();
        log.info("{}", stream);
        //  默认值OptionalInt
        log.info("{}", menu.stream()
                .mapToInt(Dish::getCalories)
                .max()
                //如果没有最大值的话，显式提供一个默认最大值
                .orElse(1));

        // 数值范围
        log.info("{}", IntStream.rangeClosed(1, 100)
                .filter(n -> n % 2 == 0)
                .count());
        // 勾股数
        IntStream.rangeClosed(1, 100)
                .boxed()
                .flatMap(a -> IntStream.rangeClosed(a, 100)
                        .mapToObj(b -> new double[]{a, b, Math.sqrt((double) a * a + b * b)})
                        .filter(t -> t[2] % 1 == 0))
                .limit(3)
                .forEach(t ->
                        log.info("{}, {}, {}", (int) t[0], (int) t[1], (int) t[2]));

        // 由值创建流
        Stream.of("Java 8 ", "Lambdas ", "In ", "Action")
                .map(String::toUpperCase)
                .forEach(t -> log.info("{}", t));
        // 空流
        Stream.empty().forEach(t -> log.info("{}", t));
        // 由数组创建流
        log.info("{}", Arrays.stream(new int[]{2, 3, 5, 7, 11, 13}).sum());
        // 由文件生成流
        long uniqueWords = 0;
        try (Stream<String> lines = Files.lines(
                Paths.get("/Users/zephyr/IdeaProjects/Java8Demo/src/main/resources/data.txt"),
                Charset.defaultCharset())) {

            uniqueWords = lines
                    .flatMap(line -> Arrays.stream(line.split(" ")))
                    .distinct()
                    .count();
        } catch (IOException e) {
            // ignore
        }
        log.info("{}", uniqueWords);
        // 创建无限流
        Stream.iterate(0, n -> n + 2)
                .limit(10)
                .forEach(t -> log.info("{}", t));
        Stream.generate(Math::random)
                .limit(5)
                .forEach(t -> log.info("{}", t));

        // 连接字符串
        String shortMenu = menu.stream().map(Dish::getName).collect(Collectors.joining(", "));
        log.info("{}", shortMenu);

        // 分组
        log.info("{}", transactions.stream().collect(Collectors.groupingBy(Transaction::getYear)));
        log.info("{}", menu.stream().collect(Collectors.groupingBy(Dish::getType)));
        log.info("{}", menu.stream().collect(Collectors.groupingBy(StreamAPIDemo::getCaloricLevel)));

        // 多级分组
        log.info("{}", menu.stream().collect(
                Collectors.groupingBy(Dish::getType,
                        Collectors.groupingBy(StreamAPIDemo::getCaloricLevel))));

        // 按子组收集数据
        log.info("{}", menu.stream().collect(
                Collectors.groupingBy(Dish::getType, Collectors.counting())));

        // 查找每个子组中热量最高的Dish
        log.info("{}", menu.stream()
                .collect(Collectors.groupingBy(Dish::getType,
                        Collectors.collectingAndThen(
                                Collectors.maxBy(Comparator.comparingInt(Dish::getCalories)),
                                Optional::get))));
        log.info("{}", menu.stream()
                .collect(Collectors.toMap(Dish::getType, Function.identity(), BinaryOperator.maxBy(Comparator.comparingInt(Dish::getCalories)))));

        // 分区
        // 分区是分组的特殊情况, 分区函数返回一个布尔值，这意味着得到的分组Map的键类型是Boolean，于是它最多可以分为两组——true是一组，false是一组。
        log.info("{}", menu.stream().collect(Collectors.partitioningBy(Dish::isVegetarian)));

        // 分区二级分组
        log.info("{}", menu.stream().collect(
                Collectors.partitioningBy(Dish::isVegetarian,
                        Collectors.groupingBy(Dish::getType))));

        // 多级分区
        log.info("{}", menu.stream().collect(
                Collectors.partitioningBy(Dish::isVegetarian,
                        Collectors.partitioningBy(d -> d.getCalories() > 500))));

        // 将数字按质数和非质数分区
        log.info("{}", IntStream.rangeClosed(2, 100)
                .boxed()
                .collect(
                        Collectors.partitioningBy(StreamAPIDemo::isPrime)));

        // 求差集
        List<Integer> list1 = Arrays.asList(1,3,5);
        List<Integer> list2 = Arrays.asList(3,5);
        log.info("{}", list1.stream().filter(t-> !list2.contains(t)).collect(Collectors.toList()));

    }

    private static Dish.CaloricLevel getCaloricLevel(Dish dish) {
        if (dish.getCalories() <= 400) {
            return Dish.CaloricLevel.DIET;
        } else if (dish.getCalories() <= 700) {
            return Dish.CaloricLevel.NORMAL;
        } else {
            return Dish.CaloricLevel.FAT;
        }
    }

    // 测试某一个待测数字是否是质数
    private static boolean isPrime(int candidate) {
        int candidateRoot = (int) Math.sqrt(candidate);
        return IntStream.rangeClosed(2, candidateRoot)
                .noneMatch(i -> candidate % i == 0);
    }
}
