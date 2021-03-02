package zephyr.opentelemetry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// https://opentelemetry.io/docs/java/automatic_instrumentation/#exporters
@SpringBootApplication
public class OpentelemetryDemoApplication {

    // vm options:
    // -javaagent:/Users/zhonghaoyuan/IdeaProjects/treasure-box-gradle/treasure-box-opentelemetry/agent/opentelemetry-javaagent-all.jar -Dotel.javaagent.debug=false
    // env:
    // OTEL_EXPORTER=otlp,logging;OTEL_EXPORTER_OTLP_ENDPOINT=localhost:55680;OTEL_EXPORTER_OTLP_INSECURE=true;OTEL_RESOURCE_ATTRIBUTES=service.name=JavaOtelDemoService
    public static void main(String[] args) {
        SpringApplication.run(OpentelemetryDemoApplication.class, args);
    }

}
