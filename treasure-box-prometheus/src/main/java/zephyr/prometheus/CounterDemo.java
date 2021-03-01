package zephyr.prometheus;

import io.prometheus.client.Counter;
import io.prometheus.client.Gauge;
import io.prometheus.client.Histogram;
import io.prometheus.client.Summary;
import io.prometheus.client.exporter.HTTPServer;
import lombok.SneakyThrows;

import java.util.concurrent.*;

// <namespace>_<subsystem>_<name>_<unit>
public class CounterDemo {

    private static final ExecutorService executorService =
            new ThreadPoolExecutor(
                    10,
                    10,
                    0L,
                    TimeUnit.MILLISECONDS,
                    new LinkedBlockingQueue<>(200),
                    Executors.defaultThreadFactory(),
                    new ThreadPoolExecutor.DiscardPolicy()
            );

    private static final String[] counterLabels = new String[]{"service", "path"};
    private static final String[] gaugeLabels = new String[]{"branch", "window"};

    private static final String[] serviceValues = new String[]{"demo服务1", "demo服务2"};
    private static final String[] branchValues = new String[]{"支行1", "支行2"};
    private static final String[] windowValues = new String[]{"窗口1", "窗口2", "窗口3"};
    private static final String[] paths = new String[]{"/healthCheck", "/demo"};

    private static final Counter httpRequest = Counter.build().name("requests_total")
            .labelNames(counterLabels)
            .help("请求总数.")
            .register();

    private static final Gauge inProgress = Gauge.build().name("inprogress_requests")
            .labelNames(gaugeLabels)
            .help("处理中的请求数.")
            .register();

    private static final Summary receivedBytes = Summary.build()
            .name("requests_size_bytes").help("请求字节数.").register();
    private static final Summary requestLatency = Summary.build()
            .name("requests_latency_seconds")
            .quantile(0.5, 0.05)   // Add 50th percentile (= median) with 5% tolerated error
            .quantile(0.9, 0.01)   // Add 90th percentile with 1% tolerated error
            .help("请求延迟(单位秒).").register();

    private static final Histogram requestLatencyHistogram = Histogram.build()
            .name("requests_latency_seconds_histogram")
            .buckets(new double[]{10, 20, 30, 40, 50, 60, 70, 80, 90, 100, 110, 120, 130, 140, 150, 160, 170, 180, 190, 200})
            .labelNames(gaugeLabels)
            .help("请求延迟(单位秒).")
            .register();

    @SneakyThrows
    public static void main(String[] args) {
        // prometheus endpoint
        new HTTPServer(8080, true);
        while (true) {
            executorService.submit(CounterDemo::processRequest);
            Thread.sleep(100L);
        }
    }

    @SneakyThrows
    private static void processRequest() {
//        httpRequest
//                .labels(getRandomLabel(serviceValues), getRandomLabel(paths))
//                .inc();
//        Gauge.Child gaugeLabeled = inProgress.labels(getRandomLabel(branchValues), getRandomLabel(windowValues));
//        Summary.Timer requestTimer = requestLatency.startTimer();
        Histogram.Timer histogramTimer = requestLatencyHistogram.labels(getRandomLabel(branchValues), getRandomLabel(windowValues)).startTimer();

        try {
//            gaugeLabeled.inc();
            Thread.sleep(ThreadLocalRandom.current().nextInt(20) * 10000L);
//            gaugeLabeled.dec();
        } finally {
//            receivedBytes.observe(ThreadLocalRandom.current().nextDouble(1024D));
//            requestTimer.observeDuration();
            histogramTimer.observeDuration();
        }
    }


    private static String getRandomLabel(String[] labelValues) {
        return labelValues[ThreadLocalRandom.current().nextInt(labelValues.length)];
    }
}
