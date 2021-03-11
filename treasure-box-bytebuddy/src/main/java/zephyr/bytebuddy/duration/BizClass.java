package zephyr.bytebuddy.duration;

import java.util.concurrent.ThreadLocalRandom;

public class BizClass {

    public String queryUserInfo(String uid, String token) throws
            InterruptedException {
        Thread.sleep(ThreadLocalRandom.current().nextInt(500));
        return uid + ":" + token;
    }

}
