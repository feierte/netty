package io.netty.demo.example;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.demo.codec.MessageDecoder;
import io.netty.demo.codec.MessageEncoder;
import io.netty.util.CharsetUtil;

/**
 * @author Jie Zhao
 * @date 2021/7/12 20:35
 */
public class NettyClient01 {

    public static void main(String[] args) throws InterruptedException {
        // 1、创建线程组
        EventLoopGroup group = new NioEventLoopGroup();
        // 2、创建客户端启动助手
        Bootstrap bootstrap = new Bootstrap();
        // 3、设置线程组
        bootstrap.group(group)
                .channel(NioSocketChannel.class) // 4、设置客户端通道实现为NIO
                .handler(new ChannelInitializer<SocketChannel>() { // 5、创建一个通道初始化对象
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        // 添加解码器
                        ch.pipeline().addLast("messageDecoder", new MessageDecoder());

                        // 添加编码器
                        ch.pipeline().addLast("messageEncoder", new MessageEncoder());

                        // 6、向pipeline中添加自定义业务处理handler
                        ch.pipeline().addLast(new NettyClientHandler());
                    }
                });

        // 7、启动客户端，等待连接服务器，同时将异步改为同步
        ChannelFuture future = bootstrap.connect("localhost", 9999).sync();
        // 8、关闭通道和关闭连接池
        future.channel().closeFuture().sync();
        group.shutdownGracefully();
    }

    public static class NettyClientHandler implements ChannelInboundHandler {

        /**
         * 通道就绪事件
         *
         * @param ctx
         * @throws Exception
         */
        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            // ctx.writeAndFlush(Unpooled.copiedBuffer("你好，我是客户端！", CharsetUtil.UTF_8));
            ctx.writeAndFlush("你好，我是客户端！");
        }

        /**
         * 通道读就绪事件
         * @param ctx
         * @param msg
         * @throws Exception
         */
        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            // ByteBuf byteBuf = (ByteBuf) msg;
            // System.out.println("服务端发送的消息：" + byteBuf.toString(CharsetUtil.UTF_8));
            System.out.println("服务端发送的消息：" + msg);
            // log.info("服务端发送的消息：{}", byteBuf.toString(CharsetUtil.UTF_8));
        }

        @Override
        public void handlerAdded(ChannelHandlerContext ctx) throws Exception {

        }

        @Override
        public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {

        }

        @Override
        public void channelRegistered(ChannelHandlerContext ctx) throws Exception {

        }

        @Override
        public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {

        }

        @Override
        public void channelInactive(ChannelHandlerContext ctx) throws Exception {

        }

        @Override
        public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {

        }

        @Override
        public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {

        }

        @Override
        public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {

        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {

        }
    }
}
