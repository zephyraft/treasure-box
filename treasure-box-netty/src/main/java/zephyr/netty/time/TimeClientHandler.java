package zephyr.netty.time;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class TimeClientHandler extends ChannelInboundHandlerAdapter {

    // 每当从客户端接收到新数据时，都会调用此方法
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        // POJO instead of ByteBuf
        UnixTime unixTime = (UnixTime) msg;
        log.info("{}", unixTime);
        ctx.close();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        // Close the connection when an exception is raised.
        log.error(cause.getMessage(), cause);
        ctx.close();
    }
}
