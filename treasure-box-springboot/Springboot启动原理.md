# springboot启动原理

https://www.cnblogs.com/baroque/p/12990138.html
## 通过Springboot的插件打成Fat jar包

普通jar包，如果要通过java -jar执行，那么META-INF中的MANIFEST.MF里，Main-Class是应用自己的Class
Springboot应用的jar包是Springboot插件在构建时，将应用的代码替换成了Spring-boot-loader的代码，应用代码和依赖的jar包被移入了BOOT-INF文件夹
包含classes（应用代码编译成的字节码）和lib（依赖的jar包），2.3.0版本后新增classpath.idx文件(依赖jar包的清单)

通过JarLauncher启动
继承了抽象类Launcher，支持文件系统和jar两种类型的启动
文件系统 java org.springframework.boot.loader.jarLauncher
jar类型 java -jar xxx.jar

launch分为三步
registerUrlProtocolHandler(注册url协议处理器)
创建类加载器（LaunchedURLClassLoader）
启动（通过MainMethodRunner.run() 反射调用MANIFEST.MF的start-class的main方法）

URL协议对应了URLStreamHandler抽象类的实现，jdk默认支持file，http，jar等协议。
springboot覆盖了jar协议默认的URLStreamHandler，处理jar内嵌的jar包