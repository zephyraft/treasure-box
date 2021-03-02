package zephyr.jdk8;

import lombok.extern.slf4j.Slf4j;
import zephyr.jdk8.model.Car;
import zephyr.jdk8.model.Insurance;
import zephyr.jdk8.model.Person;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;


@Slf4j
public class OptionalDemo {

    public static void main(String[] args) {
        Person person = new Person();
        Car car = new Car();
        Insurance insurance = new Insurance();

        // 声明一个空的Optional
        Optional<Car> optionalCar = Optional.empty();
        log.info("{}", optionalCar);

        // 依据一个非空值创建Optional
        optionalCar = Optional.of(car);
        log.info("{}", optionalCar);

        // 可接受null的Optional
        optionalCar = Optional.ofNullable(null);
        log.info("{}", optionalCar);


        // 使用 map 从 Optional 对象中提取和转换值

        // Optional的map类似SreamAPI的map
        Optional<Insurance> optInsurance = Optional.ofNullable(insurance);
        Optional<String> name = optInsurance.map(Insurance::getName);
        log.info("{}", name);

        // 解决嵌套式optional结构
        Optional<Car> optCar = Optional.of(car);
        Optional<Person> optPerson = Optional.of(person);

        optCar.ifPresent(c -> c.setInsurance(Optional.of(new Insurance())));
        optPerson.ifPresent(p -> p.setCar(optCar));

        String personName = optPerson.flatMap(Person::getCar)
                .flatMap(Car::getInsurance)
                .map(Insurance::getName)
                .orElse("Unknown"); //如果Optional的结果值为空，设置默认值
        log.info("{}", personName);

        // 解引用
        Optional<String> optionalString = Optional.ofNullable(null);
        try {
            log.info("{}", optionalString.get());
            log.info("{}", optionalString.orElseThrow(() -> throwMyException()));
        } catch (Exception e) {
            // ignore
        }
        log.info("{}", optionalString.orElse("bbbb"));
        log.info("{}", optionalString.orElseGet(() -> timeConsumingMethod()));
        optionalString.ifPresent(t -> log.info("{}", t));

        // 两个 Optional 对象的组合
        log.info("{}", nullSafeFindCheapestInsurance(Optional.empty(), Optional.empty()));

        // 使用 filter 剔除特定的值
        optInsurance.filter(i ->
                "CambridgeInsurance".equals(i.getName()))
                .ifPresent(x -> System.out.println("ok"));


        // 用 Optional 封装可能为 null 的值
        Map<String, Object> map = new HashMap<>();
        Optional<Object> value = Optional.ofNullable(map.get("key"));
        log.info("{}", value);

        // 异常与 Optional 的对比
        String s = "aaa";
        log.info("{}", stringToInt(s));
        // 不推荐使用基础类型的Optional对象（OptionalInt、OptionalLong以及OptionalDouble）
        // 因为基础类型的Optional不支持map、flatMap以及filter方法
    }

    private static String timeConsumingMethod() {
        return "ccccc";
    }


    private static RuntimeException throwMyException() {
        return new RuntimeException("my exception");
    }

    // 原方法
    private static Insurance findCheapestInsurance(Person person, Car car) {
        // 不同的保险公司提供的查询服务
        // 对比所有数据
        Insurance insurance = new Insurance();
        insurance.setName("CheapestInsurance");
        return insurance;
    }

    // null-safe 方法
    private static Optional<Insurance> nullSafeFindCheapestInsurance(Optional<Person> person, Optional<Car> car) {
        return person.flatMap(p -> car.map(c -> findCheapestInsurance(p, c)));
    }

    public static int readDuration(Properties props, String name) {
        String value = props.getProperty(name);
        if (value != null) {
            try {
                int i = Integer.parseInt(value);
                if (i > 0) {
                    return i;
                }
            } catch (NumberFormatException nfe) { }
        }
        return 0;
    }

    public static Optional<Integer> stringToInt(String s) {
        try {
            return Optional.of(Integer.parseInt(s));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

    public static int readDurationOptional(Properties props, String name) {
        return Optional.ofNullable(props.getProperty(name))
                .flatMap(OptionalDemo::stringToInt)
                .filter(i -> i > 0)
                .orElse(0);
    }
}
