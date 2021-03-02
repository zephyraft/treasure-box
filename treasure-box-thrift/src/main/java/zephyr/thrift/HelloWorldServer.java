package zephyr.thrift;

import lombok.extern.slf4j.Slf4j;
import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TTransportException;
import zephyr.thrift.api.HelloWorldService;
import zephyr.thrift.impl.HelloWorldServiceImpl;


@Slf4j
public class HelloWorldServer {

    public static void main(String[] args) throws TTransportException {
        log.info("服务端开启");

        // 关联处理器
        TProcessor processor = new HelloWorldService.Processor<>(new HelloWorldServiceImpl());
        // 设置服务端口
        TServerSocket serverSocket = new TServerSocket(8080);
        // 简单的单线程服务模型
        TServer.Args tArgs = new TServer.Args(serverSocket);
        tArgs.processor(processor);
        // 设置协议工厂为 TBinaryProtocol.Factory
        tArgs.protocolFactory(new TBinaryProtocol.Factory());

        TServer server = new TSimpleServer(tArgs);
        // 启动服务
        server.serve();
    }

}
