# SPI （Service Provider Interface）
服务提供者接口 jdk 6 引入

- api: 定义spi
- impl: 实现spi
- app: 依赖api和impl，调用服务

## 案例
- slf4j: (1.8+, 1.7以前使用StaticLoggerBinder) 不修改代码的情况下，自由切换日志框架
  - org.slf4j.spi.SLF4JServiceProvider 定义spi
  - org.slf4j.LoggerFactory 使用ServiceLoader加载spi实现
- jdbc:
  - java.sql.Driver 定义spi
  - java.sql.DriverManager 使用ServiceLoader加载spi实现
- Flink: SPI机制在Flink的Table模块中也有广泛应用——因为Flink Table的类型有很多种，同样非常适合插件化。
  - TableFactory spi
  - TableFactoryService load spi service 