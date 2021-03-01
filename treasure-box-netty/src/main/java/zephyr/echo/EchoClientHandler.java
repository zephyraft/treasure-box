package zephyr.echo;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by zephyr on 2019-09-20.
 */
@Slf4j
@ChannelHandler.Sharable
public class EchoClientHandler extends SimpleChannelInboundHandler<ByteBuf> {

    public void channelActive(ChannelHandlerContext ctx) {
        // 当被通知Channel是活跃的时候，发送一条消息
        ctx.writeAndFlush(Unpooled.copiedBuffer("Netty rocks!", CharsetUtil.UTF_8));
    }

    // 当从服务器接收到一条消息时被调用
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) {
        log.info("Client received: {}", msg.toString(CharsetUtil.UTF_8));
    }

    // 在处理过程中引发异常时调用
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        log.error(cause.getMessage(), cause);
        ctx.close();
    }
}
