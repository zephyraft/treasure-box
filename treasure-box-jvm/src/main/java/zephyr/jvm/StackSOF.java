package zephyr.jvm;

import lombok.extern.slf4j.Slf4j;

/**
 * 栈溢出
 * VMArgs：-Xss128k
 */
@Slf4j
public class StackSOF {

    private int stackLength = 1;

    public static void main(String[] args) {
        StackSOF oom = new StackSOF();
        try {
            oom.stackLeak();
        } catch (Throwable e) {
            log.error("stack length:{}", oom.stackLength);
            log.error(e.getMessage(), e);
        }
    }

    public void stackLeak() {
        stackLength ++;
        stackLeak();
    }

}
