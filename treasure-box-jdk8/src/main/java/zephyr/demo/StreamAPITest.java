package zephyr.demo;

import lombok.extern.slf4j.Slf4j;
import zephyr.model.ModelFactory;
import zephyr.model.Trader;
import zephyr.model.Transaction;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by zephyr on 2018/12/11.
 */
@Slf4j
public class StreamAPITest {

    public static void main(String[] args) {
        List<Transaction> transactions = ModelFactory.getTransaction();

        // 找出2011年发生的所有交易，并按交易额排序（从低到高）。
        log.info("{}", transactions.stream()
                .filter(t -> t.getYear() == 2011)
                .sorted(Comparator.comparingInt(Transaction::getValue))
                .collect(Collectors.toList()));

        // 交易员都在哪些不同的城市工作过？
        log.info("{}", transactions.stream()
                .map(t -> t.getTrader().getCity())
                .distinct()
                .collect(Collectors.toList()));

        // 查找所有来自于剑桥的交易员，并按姓名排序。
        log.info("{}", transactions.stream()
                .map(Transaction::getTrader)
                .filter(t -> "Cambridge".equals(t.getCity()))
                .distinct()
                .sorted(Comparator.comparing(Trader::getName))
                .collect(Collectors.toList()));

        // 返回一个字符串，包含所有交易员的姓名，按字母顺序排序。
        String nameString = transactions.stream()
                .map(t -> t.getTrader().getName())
                .distinct()
                .sorted()
                .collect(Collectors.joining());
        log.info("{}", nameString);

        // 有没有交易员是在米兰工作的？
        log.info("{}", transactions.stream()
                .anyMatch(t -> "Milan".equals(t.getTrader().getCity())));

        // 逐个打印生活在剑桥的交易员的交易额。
        transactions.stream()
                .filter(t -> "Cambridge".equals(t.getTrader().getCity()))
                .map(Transaction::getValue)
                .forEach(t -> log.info("{}", t));


        // 所有交易中，最高的交易额是多少？
        log.info("{}", transactions.stream()
                .map(Transaction::getValue)
                .reduce(Integer::max));


        // 找到交易额最小的交易。
        log.info("{}", transactions.stream()
                .min(Comparator.comparing(Transaction::getValue)));
    }

}
