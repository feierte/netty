package io.netty.demo.example.chatroom;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author Jie Zhao
 * @date 2021/7/15 20:50
 */
public class ChatClientHandler extends SimpleChannelInboundHandler<String> {

    /**
     * 通道读取就绪事件
     * @param ctx           the {@link ChannelHandlerContext} which this {@link SimpleChannelInboundHandler}
     *                      belongs to
     * @param msg           the message to handle
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println(msg);
    }
}
