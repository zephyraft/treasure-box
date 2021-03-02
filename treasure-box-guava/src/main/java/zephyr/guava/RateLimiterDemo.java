package zephyr.guava;

import com.google.common.util.concurrent.RateLimiter;

import java.util.concurrent.TimeUnit;

/**
 * Guava的 RateLimiter提供了令牌桶算法实现：平滑突发限流(SmoothBursty)和平滑预热限流(SmoothWarmingUp)实现。
 */
public class RateLimiterDemo {

    public static void main(String[] args) throws InterruptedException {

        // 平滑突发限流 SmoothBursty
        final RateLimiter rateLimiter = RateLimiter.create(5); // 每秒5个

        // 平滑产生令牌
        for (int i = 0; i < 10; i++ ){
            System.out.println("获取1个令牌花费的时间:" + rateLimiter.acquire() + "s");
        }

        // 滞后效应，需要替前一个请求进行等待
        System.out.println("获取5个令牌花费的时间:" + rateLimiter.acquire(5) + "s");
        for (int i = 0; i < 5; i++ ){
            System.out.println("获取1个令牌花费的时间:" + rateLimiter.acquire() + "s");
        }

        // 累积令牌 应对突发流量
        Thread.sleep(2000L);
        for (int i = 0; i < 10; i++ ){
            System.out.println("获取1个令牌花费的时间:" + rateLimiter.acquire() + "s");
        }

        System.out.println("带预热期");
        // 平滑预热限流 SmoothWarmingUp
        // 生成令牌的速率刚启动时比较低，逐步提高到设定值
        // 适合系统刚启动需要一点时间来“热身”的场景
        final RateLimiter warmingUpRateLimiter = RateLimiter.create(5, 3, TimeUnit.SECONDS); // 每秒5个 预热期3秒
        for (int i = 0; i < 20; i++ ){
            System.out.println("获取1个令牌花费的时间:" + warmingUpRateLimiter.acquire() + "s");
        }

    }

}
