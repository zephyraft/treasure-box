package zephyr.jdk8;

import lombok.extern.slf4j.Slf4j;

import java.util.function.Function;
import java.util.stream.LongStream;
import java.util.stream.Stream;


@Slf4j
public class ParallelStreamAPIDemo {

    private static final int MEASURE_TIMES = 1;

    public static void main(String[] args) {
        // 求和方法的并行版本比顺序版本要慢很多
        // iterate生成的是装箱的对象，必须拆箱成数字才能求和；
        // 我们很难把iterate分成多个独立块来并行执行
        log.info("== Sequential sum done in:{} msecs",
                measureSumPerf(ParallelStreamAPIDemo::sequentialSum, 10_000_000));
        log.info("== Iterative sum done in:{} msecs",
                measureSumPerf(ParallelStreamAPIDemo::iterativeSum, 10_000_000));
        log.info("== Parallel sum done in:{} msecs",
                measureSumPerf(ParallelStreamAPIDemo::parallelSum, 10_000_000));

        // LongStream.rangeClosed直接产生原始类型的long数字，没有装箱拆箱的开销
        // LongStream.rangeClosed会生成数字范围，很容易拆分为独立的小块
        log.info("== Range sum done in:{} msecs",
                measureSumPerf(ParallelStreamAPIDemo::rangedSum, 10_000_000));
        log.info("== Parallel Range sum done in:{} msecs",
                measureSumPerf(ParallelStreamAPIDemo::parallelRangedSum, 10_000_000));

        // 错用并行流而产生错误的首要原因，就是使用的算法改变了某些共享状态
        log.info("== SideEffect sum done in:{} msecs",
                measureSumPerf(ParallelStreamAPIDemo::sideEffectSum, 10_000_000));
        // 会得到错误的结果
        log.info("== Parallel SideEffect sum done in:{} msecs",
                measureSumPerf(ParallelStreamAPIDemo::sideEffectParallelSum, 10_000_000));

        // 是否使用并行流？
        // 1.如果有疑问，测量。用适当的基准来检查其性能。
        // 2.留意装箱。自动装箱和拆箱操作会大大降低性能。
        // 3.有些操作本身在并行流上的性能就比顺序流差。依赖于元素顺序的操作，在并行流上执行的代价非常大
        //  （例如limit, findFirst，并行性能差，findAny会比findFirst性能好，因为它不一定要按顺序来执行）
        //   你总是可以调用unordered方法来把有序流变成无序流。
        //   对无序并行流调用limit可能会比单个有序流（比如数据源是一个List）更高效
        // 4.考虑流的操作流水线的总计算成本. 设N是要处理的元素的总数，Q是一个元素通过
        //   流水线的大致处理成本，则N*Q就是这个对成本的一个粗略的定性估计。Q值较高就意味
        //   着使用并行流时性能好的可能性比较大。
        // 5.对于较小的数据量，选择并行流几乎从来都不是一个好的决定。
        // 6.考虑流背后的数据结构是否易于分解。 例如，ArrayList的拆分效率比LinkedList
        //高得多，因为前者用不着遍历就可以平均拆分，而后者则必须遍历
        // 7.流自身的特点，以及流水线中的中间操作修改流的方式，都可能会改变分解过程的性能。
        //   例如，一个SIZED流可以分成大小相等的两部分，这样每个部分都可以比较高效地并行处
        //   理，但筛选操作可能丢弃的元素个数却无法预测，导致流本身的大小未知。
        // 8.还要考虑终端操作中合并步骤的代价是大是小

    }

    public static long measureSumPerf(Function<Long, Long> adder, long n) {
        long fastest = Long.MAX_VALUE;
        for (int i = 0; i < MEASURE_TIMES; i++) {
            long start = System.nanoTime();
            long sum = adder.apply(n);
            long duration = (System.nanoTime() - start) / 1_000_000;
            log.info("Result: {}", sum);
            if (duration < fastest) fastest = duration;
        }
        return fastest;
    }

    private static long iterativeSum(long n) {
        long result = 0;
        for (long i = 1L; i <= n; i++) {
            result += i;
        }
        return result;
    }

    private static long sequentialSum(long n) {
        return Stream
                //生成自然数无限流
                .iterate(1L, i -> i + 1)
                .limit(n)
                //对所有数字求和来归纳流
                .reduce(0L, Long::sum);
    }

    private static long parallelSum(long n) {
        return Stream
                .iterate(1L, i -> i + 1)
                .limit(n)
                //转换为并行流
                .parallel()
                .reduce(0L, Long::sum);
    }

    private static long rangedSum(long n) {
        return LongStream.rangeClosed(1, n)
                .reduce(0L, Long::sum);
    }

    private static long parallelRangedSum(long n) {
        return LongStream.rangeClosed(1, n)
                .parallel()
                .reduce(0L, Long::sum);
    }

    private static long sideEffectSum(long n) {
        Accumulator accumulator = new Accumulator();
        LongStream.rangeClosed(1, n).forEach(accumulator::add);
        return accumulator.total;
    }

    private static long sideEffectParallelSum(long n) {
        Accumulator accumulator = new Accumulator();
        LongStream.rangeClosed(1, n).parallel().forEach(accumulator::add);
        return accumulator.total;
    }

    private static class Accumulator {
        public long total = 0;
        public void add(long value) { total += value; }
    }
}
