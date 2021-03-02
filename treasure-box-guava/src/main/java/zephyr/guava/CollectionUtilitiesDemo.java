package zephyr.guava;

import com.google.common.collect.*;
import com.google.common.primitives.Ints;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Slf4j
public class CollectionUtilitiesDemo {

    public static void main(String[] args) {
        // Static constructors
        final List<String> list = Lists.newArrayList("alpha", "beta", "gamma");
        log.info("{}", list);
        // 精确100
        List<String> exactly100 = Lists.newArrayListWithCapacity(100);
        // 大约100
        List<String> approx100 = Lists.newArrayListWithExpectedSize(100);

        // Iterables
        Iterable<Integer> concatenated = Iterables.concat(
                Ints.asList(1, 2, 3),
                Ints.asList(4, 5, 6)
        );

        log.info("{}", concatenated);
        log.info("{}", Iterables.getLast(concatenated));
        log.info("{}", Iterables.getOnlyElement(Lists.newArrayList("a")));

        List<Integer> countUp = Ints.asList(1, 2, 3, 4, 5);
        List<Integer> countDown = Lists.reverse(countUp); // {5, 4, 3, 2, 1}
        List<List<Integer>> parts = Lists.partition(countUp, 2); // {{1, 2}, {3, 4}, {5}}

        log.info("");
        log.info("Lists");
        log.info("{}", countUp);
        log.info("{}", countDown);
        log.info("{}", parts);

        Set<String> wordsWithPrimeLength = ImmutableSet.of("one", "two", "three", "six", "seven", "eight");
        Set<String> primes = ImmutableSet.of("two", "three", "five", "seven");
        // 交集
        Sets.SetView<String> intersection = Sets.intersection(primes, wordsWithPrimeLength);

        log.info("");
        log.info("Sets");
        log.info("{}", wordsWithPrimeLength);
        log.info("{}", primes);
        log.info("{}", intersection);

        Set<String> animals = ImmutableSet.of("gerbil", "hamster");
        Set<String> fruits = ImmutableSet.of("apple", "orange", "banana");
        // 笛卡尔积
        Set<List<String>> product = Sets.cartesianProduct(animals, fruits);
        // {{"gerbil", "apple"}, {"gerbil", "orange"}, {"gerbil", "banana"},
        //  {"hamster", "apple"}, {"hamster", "orange"}, {"hamster", "banana"}}
        // 所有子集
        Set<Set<String>> animalSets = Sets.powerSet(animals);
        // {{}, {"gerbil"}, {"hamster"}, {"gerbil", "hamster"}}

        log.info("");
        log.info("{}", product);
        log.info("{}", animalSets);

        // uniqueIndex
        ImmutableMap<Integer, String> stringsByIndex = Maps.uniqueIndex(animals, s -> s != null ? s.length() : 0);
        log.info("");
        log.info("{}", stringsByIndex);

        // difference
        Map<String, Integer> left = ImmutableMap.of("a", 1, "b", 2, "c", 3);
        Map<String, Integer> right = ImmutableMap.of("b", 2, "c", 4, "d", 5);
        MapDifference<String, Integer> diff = Maps.difference(left, right);

        log.info("");
        log.info("{}", diff.entriesInCommon()); // {"b" => 2}
        log.info("{}", diff.entriesDiffering()); // {"c" => (3, 4)}
        log.info("{}", diff.entriesOnlyOnLeft()); // {"a" => 1}
        log.info("{}", diff.entriesOnlyOnRight()); // {"d" => 5}
    }

}
