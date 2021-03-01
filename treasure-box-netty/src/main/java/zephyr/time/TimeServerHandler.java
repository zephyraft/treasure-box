package zephyr.time;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

/**
 * 实现TIME协议
 * 发送包含32位整数的消息，而不接收任何请求，并在发送消息后关闭连接
 * Created by zephyr on 2019-09-20.
 */
@Slf4j
public class TimeServerHandler extends ChannelInboundHandlerAdapter {

    // 当建立连接并准备生成流量时，将调用该方法
    @Override
    public void channelActive(final ChannelHandlerContext ctx) throws Exception {
        // POJO instead of ByteBuf
        UnixTime time = new UnixTime();

        // 表示异步发生的I / O操作
        final ChannelFuture f = ctx.writeAndFlush(time);
        // 监听
        f.addListener((ChannelFutureListener) future -> {
            // 在ChannelFuture完成后调用该方法
            assert f == future;
            // 关闭连接
            ctx.close();
        });
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        // Close the connection when an exception is raised.
        log.error(cause.getMessage(), cause);
        ctx.close();
    }
}
