package zephyr.echo;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;

/**
 * 实现Echo
 * Created by zephyr on 2019-09-10.
 */
@Slf4j
@ChannelHandler.Sharable // 表示一个ChannelHandler可以被多个Channel安全共享
public class EchoServerHandler extends ChannelInboundHandlerAdapter {

    // 对于每个传入的消息都要调用
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf in = (ByteBuf) msg;
        log.info("{}", in.toString(StandardCharsets.UTF_8));
        // 将收到的消息写给发送者，而不冲刷
        ctx.write(in);
    }

    // 当前批量读取中的最后一条消息时调用
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        // 将未决消息冲刷到远程节点
        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER)
                // 关闭channel
                .addListener(ChannelFutureListener.CLOSE);
    }

    // 有异常抛出时会调用
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        log.error(cause.getMessage(), cause);
        ctx.close();
    }
}
