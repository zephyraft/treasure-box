package zephyr.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import zephyr.service.api.HelloWorldService;

/**
 * Created by zephyr on 2020/6/5.
 */
@Slf4j
public class HelloWorldClient {

    public static void main(String[] args) {
        log.info("客户端开启");

        // 设置调用的服务地址为本地，端口为8080,超时设置为2秒
        try (TTransport transport = new TSocket("localhost", 8080, 2000)) {
            // 协议要和服务端一致
            TProtocol protocol = new TBinaryProtocol(transport);
            HelloWorldService.Client client = new HelloWorldService.Client(protocol);
            transport.open();
            // 调用接口
            String result = client.sendString("Hello World");
            log.info("服务端返回:{}", result);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

    }

}
