package zephyr.netty.time;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

/**
 * TIME解码器
 * 处理基于流的传输
 * 在基于流的传输（例如TCP / IP）中，接收的数据存储在套接字接收缓冲区中。
 * 不幸的是，基于流的传输的缓冲区不是数据包队列而是字节队列。
 * 这意味着，即使您将两条消息作为两个独立的数据包发送，操作系统也不会将它们视为两条消息，而只是一堆字节。
 * 因此，无法保证您所阅读的内容正是您的远程同行所写的内容。
 */
public class TimeDecoder extends ReplayingDecoder {

    // 每当收到新数据时，使用内部维护的累积缓冲区调用该方法
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
        // POJO instead of ByteBuf
        out.add(new UnixTime(in.readUnsignedInt()));
    }
}
