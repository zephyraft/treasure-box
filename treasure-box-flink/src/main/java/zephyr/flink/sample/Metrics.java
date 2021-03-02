package zephyr.flink.sample;

import java.time.LocalDateTime;

public class Metrics {

    private int metricId;
    private int type;
    private String host;
    private String metricName;
    private String value;
    private LocalDateTime timestamp;
    private long timeDiff; // 单位毫秒

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getMetricId() {
        return metricId;
    }

    public void setMetricId(int metricId) {
        this.metricId = metricId;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getMetricName() {
        return metricName;
    }

    public void setMetricName(String metricName) {
        this.metricName = metricName;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public long getTimeDiff() {
        return timeDiff;
    }

    public void setTimeDiff(long timeDiff) {
        this.timeDiff = timeDiff;
    }

    @Override
    public String toString() {
        return "Metrics{" +
                "metricId=" + metricId +
                ", type=" + type +
                ", host='" + host + '\'' +
                ", metricName='" + metricName + '\'' +
                ", value='" + value + '\'' +
                ", timestamp=" + timestamp +
                ", timeDiff=" + timeDiff +
                '}';
    }
}
