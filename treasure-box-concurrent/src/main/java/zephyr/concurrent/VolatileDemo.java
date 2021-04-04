package zephyr.concurrent;

// 下列例子展示，不使用volatile时，子线程读取不到主线程对flag变量的修改
public class VolatileDemo {

//    private static boolean flag = true;
    private static volatile boolean flag = true;

    public static void main(String[] args) throws InterruptedException {
        new Thread(() -> {
            while (flag) {
            }
            System.out.println("===flag is false===");
        }).start();

        Thread.sleep(100L);

        System.out.println("turn flag false");
        flag = false;
    }

}
