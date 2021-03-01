package zephyr.demo.sample;

import java.security.SecureRandom;
import java.util.Objects;
import java.util.Random;

public class MetricMapper {

    private static final Random random = new SecureRandom();

    public static void fillingMetric(Metrics metrics) {
        metrics.setMetricId(Objects.hash(metrics.getHost(), metrics.getMetricName()));
        // TODO 根据每个点的metric id，查询配置得到metric的type
        metrics.setType(random.nextInt(3));
    }

}
