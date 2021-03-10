package zephyr.asm.monitor;

public class MonitorLog {

    public static void info(String name, int... parameters) {
        System.out.println("方法：" + name);
        System.out.print("参数：");
        for (int parameter : parameters) {
            System.out.print(parameter + ",");
        }
        System.out.print("\n");
    }

}
