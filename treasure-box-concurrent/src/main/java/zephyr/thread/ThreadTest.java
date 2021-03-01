package zephyr.thread;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * 创建线程的三种方式
 */
@Slf4j
public class ThreadTest {

    public static void main(String[] args) throws InterruptedException {
        // 第一种方式，继承Thread类，不能再继承其他类
        MyThread thread = new MyThread();
        thread.start();
        // 第二种方式，实现Runnable接口
        RunnableTask runnableTask = new RunnableTask();
        new Thread(runnableTask).start();
        new Thread(runnableTask).start();
        // 第三种方式，使用FutureTask，任务实现Callable接口，可以拿到任务的返回结果
        FutureTask<String> futureTask = new FutureTask<>(new CallerTask());
        new Thread(futureTask).start();
        try {
            String result = futureTask.get();
            log.info(result);
        } catch (ExecutionException e) {
            log.error(e.getMessage(), e);
        }
    }

    static class MyThread extends Thread {
        @Override
        public void run() {
            log.info("I am a child thread extends Thread");
        }
    }

    static class RunnableTask implements Runnable {
        @Override
        public void run() {
            log.info("I am a child thread implements Runnable");
        }
    }

    static class CallerTask implements Callable<String> {
        @Override
        public String call() throws Exception {
            return "hello";
        }
    }
}
