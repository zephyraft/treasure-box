package zephyr.demo;

import lombok.extern.slf4j.Slf4j;
import zephyr.api.Discount;
import zephyr.api.Quote;
import zephyr.api.Shop;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * 并行 - 它们将一个操作切分为多个子操作，在多个不同的核、CPU甚至是机器上并行地执行这些子操作
 * (并行流，分支/合并框架)
 * <p>
 * 并发 - 在同一个CPU上执行几个松耦合的任务，充分利用CPU的核，让其足够忙碌，从而最大化程序的吞吐量
 * （Future，CompletableFuture）
 * <p>
 * Created by zephyr on 2018/12/28.
 */
@Slf4j
public class FutureDemo {

    private static final List<Shop> shops = Arrays.asList(
            new Shop("BestPrice"),
            new Shop("LetsSaveBig"),
            new Shop("MyFavoriteShop"),
            new Shop("BuyItAll"),
            new Shop("ShopEasy")
    );

    // 为“最优价格查询器”应用定制的执行器
    private static final Executor executor = Executors.newFixedThreadPool(
            Math.min(shops.size(), 100),
            r -> {
                Thread t = new Thread(r);
                t.setDaemon(true); // 使用守护线程——这种方式不会阻止程序的关停  如果将线程标记为守护进程，意味着程序退出时它也会被回收
                return t;
            }
    );

    public static void main(String[] args) {
        // 使用Future以异步的方式执行一个耗时的操作
        //future();
        // 使用 CompletableFuture 构建异步应用
        //completableFuture();

        // Nthreads = NCPU * UCPU * (1 + W/C)
        // NCPU是处理器的核的数目
        // UCPU是期望的CPU利用率 该值应该介于0和1之间
        // W/C是等待时间与计算时间的比率
        // LOGGER.info("{}", Runtime.getRuntime().availableProcessors());

        // 串行和多种并行方式的效率比较
        // 对集合进行 并行 计算有两种方式
        // 使用流还是CompletableFutures？

        // 如果你进行的是计算密集型的操作，并且没有I/O，那么推荐使用Stream接口，因为实
        // 现简单，同时效率也可能是最高的（如果所有的线程都是计算密集型的，那就没有必要
        // 创建比处理器核数更多的线程）。

        // 如果你并行的工作单元还涉及等待I/O的操作（包括网络连接等待），那么使用
        // CompletableFuture灵活性更好，你可以像前文讨论的那样，依据等待/计算，或者
        // W/C的比率设定需要使用的线程数
        //benchmark();

        // 对多个异步任务进行流水线操作
        long start;
        long duration;
        start = System.nanoTime();
        log.info("{}", findDiscountPrices("myPhone27S"));
        duration = (System.nanoTime() - start) / 1_000_000;
        log.info("Done in {} msecs", duration);
        start = System.nanoTime();
        log.info("{}", findDiscountPricesCompletableFuture("myPhone27S"));
        duration = (System.nanoTime() - start) / 1_000_000;
        log.info("Done in {} msecs", duration);

    }


    private static void benchmark() {
        long start;
        long duration;
        start = System.nanoTime();
        log.info("{}", findPrices("myPhone27S"));
        duration = (System.nanoTime() - start) / 1_000_000;
        log.info("Done in {} msecs", duration);

        start = System.nanoTime();
        log.info("{}", findPricesParallel("myPhone27S"));
        duration = (System.nanoTime() - start) / 1_000_000;
        log.info("Done in {} msecs", duration);

        start = System.nanoTime();
        log.info("{}", findPricesCompletableFuture("myPhone27S"));
        duration = (System.nanoTime() - start) / 1_000_000;
        log.info("Done in {} msecs", duration);

        start = System.nanoTime();
        log.info("{}", findPricesCompletableFutureAndExecutors("myPhone27S"));
        duration = (System.nanoTime() - start) / 1_000_000;
        log.info("Done in {} msecs", duration);
    }

    private static void completableFuture() {
        Shop shop = new Shop("BestShop");
        long start = System.nanoTime();
        Future<Double> futurePrice = shop.getPriceAsyncUseSupply("my favorite product");
        long invocationTime = ((System.nanoTime() - start) / 1_000_000);
        log.info("Invocation returned after {} msecs", invocationTime);
        // 执行更多任务，比如查询其他商店
        // doSomethingElse
        // 在计算商品价格的同时
        try {
            double price = futurePrice.get(3, TimeUnit.SECONDS);
            String priceString = String.format("%.2f", price);
            log.info("Price is {}", priceString);
        } catch (Exception e) {
            log.error("", e);
        }
        long retrievalTime = ((System.nanoTime() - start) / 1_000_000);
        log.info("Price returned after {} msecs", retrievalTime);
    }

    private static void future() {
        ExecutorService executor = Executors.newCachedThreadPool();
        Future<Double> future = executor.submit(FutureDemo::doSomeLongComputation);
        // doSomethingElse
        try {
            Double result = future.get(4, TimeUnit.SECONDS);
            log.info("{}", result);
        } catch (ExecutionException ee) {
            // 计算抛出一个异常
            log.error("", ee);
        } catch (InterruptedException ie) {
            // 当前线程在等待过程中被中断
            Thread.currentThread().interrupt();
            log.error("", ie);
        } catch (TimeoutException te) {
            // 在Future对象完成之前超过已过期
            log.error("超时", te);
        }
    }

    private static double doSomeLongComputation() throws InterruptedException {
        Thread.sleep(2000L);
        return 0.124124123D;
    }

    // 采用顺序查询所有商店的方式实现的findPrices方法
    private static List<String> findPrices(String product) {
        return shops.stream()
                .map(shop -> String.format("%s price is %.2f", shop.getName(), shop.getPrice(product)))
                .collect(Collectors.toList());
    }

    // 对findPrices进行并行操作
    private static List<String> findPricesParallel(String product) {
        return shops.parallelStream()
                .map(shop -> String.format("%s price is %.2f", shop.getName(), shop.getPrice(product)))
                .collect(Collectors.toList());
    }

    // 使用CompletableFuture实现findPrices方法
    private static List<String> findPricesCompletableFuture(String product) {
        List<CompletableFuture<String>> priceFutures =
                shops.stream()
                        .map(shop -> CompletableFuture.supplyAsync(() -> shop.getName() + " price is " + shop.getPrice(product)))
                        .collect(Collectors.toList());

        return priceFutures.stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList());
    }

    // 使用CompletableFuture实现findPrices方法
    private static List<String> findPricesCompletableFutureAndExecutors(String product) {
        List<CompletableFuture<String>> priceFutures =
                shops.stream()
                        .map(shop -> CompletableFuture.supplyAsync(() -> shop.getName() + " price is " + shop.getPrice(product), executor))
                        .collect(Collectors.toList());

        return priceFutures.stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList());
    }

    // 以最简单的方式实现使用Discount服务的findPrices方法
    private static List<String> findDiscountPrices(String product) {
        return shops.stream()
                // 取得每个shop对象 中商品的原始价格
                .map(shop -> shop.getDiscountPrice(product))
                // 在Quote对象中对shop返回的字符串进行转换
                .map(Quote::parse)
                // 联系Discount服务，为每个Quote申请折扣
                .map(Discount::applyDiscount)
                .collect(Collectors.toList());
    }

    // 使用CompletableFuture实现findPrices方法
    private static List<String> findDiscountPricesCompletableFuture(String product) {
        List<CompletableFuture<String>> priceFutures =
                shops.stream()
                        // 以异步方式取得每个shop中指定产品的原始价格
                        .map(shop -> CompletableFuture.supplyAsync(
                                () -> shop.getDiscountPrice(product), executor))
                        // Quote对象存在时，对其返回的值进行转换
                        .map(future -> future.thenApply(Quote::parse))
                        // 使用另一个异步任务构造期望的Future，申请折扣
                        .map(future -> future.thenCompose(quote ->
                                CompletableFuture.supplyAsync(
                                        () -> Discount.applyDiscount(quote), executor)))
                        .collect(Collectors.toList());

        // 等待流中的所有Future执行完毕，并提取各自的返回值
        return priceFutures.stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList());
    }

}
