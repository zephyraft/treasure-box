package zephyr.thrift.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.thrift.TException;
import zephyr.thrift.api.HelloWorldService;


@Slf4j
public class HelloWorldServiceImpl implements HelloWorldService.Iface {

    @Override
    public String sendString(String param) throws TException {
        log.info("接收到客户端传来的参数: {}", param);
        return "服务端成功收到消息";
    }
}
