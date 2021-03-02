package zephyr.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring Boot 特性
 * 创建独立的Spring应用
 * 直接嵌入Tomcat Jetty或Undertow等Web容器（不需要部署WAR文件）
 * 提供固化的starter依赖，简化构建配置
 * 当条件满足时自动地装配Spring或第三方类库
 * 提供运维（Production-Ready）特性，如指标信息（Metrics），健康检查及外部化配置
 * 绝无代码生成，并且不需要XML配置
 *
 * 应用类型：
 * 非Web应用
 *      服务提供
 *      调度任务
 *      消息处理
 * Web应用
 *      Servlet容器实现（Web）
 *      Reactive Web容器实现（WebFlux）
 */
@SpringBootApplication
public class TreasureBoxBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(TreasureBoxBootApplication.class, args);
    }

}
