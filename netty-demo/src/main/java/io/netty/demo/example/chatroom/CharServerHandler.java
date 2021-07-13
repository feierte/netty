package io.netty.demo.example.chatroom;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * 聊天室业务处理类
 *
 * @author Jie Zhao
 * @date 2021/7/13 21:07
 */
public class CharServerHandler extends SimpleChannelInboundHandler<String> {

    public static List<Channel> channelList = new ArrayList<Channel>();

    /**
     * 通道就绪事件
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        // 当有新的客户端连接的时候，将通道放入集合
        channelList.add(channel);
        System.out.println("[Server]: " + channel.remoteAddress().toString().substring(1) + "在线.");
    }

    /**
     * 通道读取事件
     *
     * @param ctx the {@link ChannelHandlerContext} which this {@link SimpleChannelInboundHandler}
     *            belongs to
     * @param msg the message to handle
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        // 当前发送消息的通道，当前发送消息的客户端
        Channel channel = ctx.channel();
        for (Channel channel1 : channelList) {
            if (channel1 == channel) {
                continue;
            }
            channel1.writeAndFlush("[" + channel.remoteAddress().toString().substring(1) + "]说的话.");
        }
    }

    /**
     * 通道未就绪事件
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        channelList.remove(channel);
        System.out.println("[Server]: " + channel.remoteAddress().toString().substring(1) + "下线.");
    }

    /**
     * 异常处理事件
     *
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        Channel channel = ctx.channel();
        channelList.remove(channel);
        System.out.println("[Server]: " + channel.remoteAddress().toString().substring(1) + "处于异常状态.");
    }
}
