package zephyr.netty.discard;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;

/**
 * 实现DISCARD协议
 */
@Slf4j
public class DiscardServerHandler extends ChannelInboundHandlerAdapter {

    // 每当从客户端接收到新数据时，都会调用此方法
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        // 处理程序有责任释放传递给处理程序的任何引用计数对象
        try {
            ByteBuf in = (ByteBuf) msg;
            log.info("{}", in.toString(StandardCharsets.UTF_8));
        } finally {
            // 静默丢弃
            ReferenceCountUtil.release(msg); // (2)
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        // Close the connection when an exception is raised.
        log.error(cause.getMessage(), cause);
        ctx.close();
    }
}
