package io.netty.demo.codec;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.util.CharsetUtil;

import java.util.List;

/**
 * 消息编码器
 *
 * @author Jie Zhao
 * @date 2021/7/13 20:42
 */
public class MessageEncoder extends MessageToMessageEncoder {
    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, List out) throws Exception {
        System.out.println("消息正在进行编码...");
        String message = (String) msg;
        out.add(Unpooled.copiedBuffer(message, CharsetUtil.UTF_8));
    }
}
