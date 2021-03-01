package zephyr.thread.pool;

import lombok.extern.slf4j.Slf4j;
import zephyr.utils.SleepUtils;

/**
 * Created by zephyr on 2020/6/2.
 */
@Slf4j
public class ThreadPoolTest {

    static ThreadPool<ThreadPoolRunner> pool = new DefaultThreadPool<>(1);

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            pool.execute(new ThreadPoolRunner(i));
        }
        log.info("job size = {}", pool.getJobSize());
        SleepUtils.second(10);
        pool.addWorkers(10);
        SleepUtils.second(10);
        pool.removeWorker(9);
        SleepUtils.second(10);
        pool.shutdown();
    }

    static class ThreadPoolRunner implements Runnable {

        private final int id;

        public ThreadPoolRunner(int id) {
            this.id = id;
        }

        @Override
        public void run() {
            log.info("job start id = {}", id);
            SleepUtils.second(5);
            log.info("job end id = {}", id);
        }
    }

}
