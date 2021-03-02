package zephyr.netty.echo;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;


public class EchoClient {

    private String serverHost;
    private int serverPort;

    public EchoClient(String serverHost, int serverPort) {
        this.serverHost = serverHost;
        this.serverPort = serverPort;
    }

    public static void main(String[] args) throws Exception {
        new EchoClient("localhost", 8080).run();
    }

    public void run() throws Exception {
        // NioEventLoopGroup 是一个处理I/O操作的多线程事件循环

        // 定义client (处理客户端连接后的时间)
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            // Bootstrap是一个Server辅助类，简化配置
            Bootstrap b = new Bootstrap();
            b.group(workerGroup)
                    // 指定channel实例类型
                    .channel(NioSocketChannel.class)
                    // 定义客户端连接后的 事件处理器
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) {
                            ch.pipeline().addLast(new EchoClientHandler());
                        }
                    })
                    // 添加额外配置
                    .option(ChannelOption.SO_KEEPALIVE, true); // 是否启用心跳保活机制

            // Bind and start to accept incoming connections.
            ChannelFuture f = b.connect(serverHost, serverPort).sync();

            // Wait until the server socket is closed.
            // In this example, this does not happen, but you can do that to gracefully
            // shut down your server.
            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
        }
    }

}
