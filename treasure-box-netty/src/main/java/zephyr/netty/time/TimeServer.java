package zephyr.netty.time;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;


public class TimeServer {

    private int port;

    public TimeServer(int port) {
        this.port = port;
    }

    // 测试命令：rdate -p localhost
    public static void main(String[] args) throws Exception {
        // TIME协议指定37端口
        new TimeServer(37).run();
    }

    public void run() throws Exception {
        // NioEventLoopGroup 是一个处理I/O操作的多线程事件循环

        // 定义acceptor (接收连接)
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        // 定义client (处理客户端连接后的时间)
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            // ServerBootstrap是一个Server辅助类，简化配置
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    // 指定channel实例类型
                    .channel(NioServerSocketChannel.class)
                    // 定义客户端连接后的 事件处理器
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) {
                            ch.pipeline().addLast(new TimeEncoder(), new TimeServerHandler());
                        }
                    })
                    // 添加额外配置
                    .option(ChannelOption.SO_BACKLOG, 128) // 临时存放已完成三次握手的请求的队列的最大长度
                    .childOption(ChannelOption.SO_KEEPALIVE, true); // 是否启用心跳保活机制

            // Bind and start to accept incoming connections.
            ChannelFuture f = b.bind(port).sync();

            // Wait until the server socket is closed.
            // In this example, this does not happen, but you can do that to gracefully
            // shut down your server.
            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

}
