package io.netty.demo.example;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.demo.codec.MessageDecoder;
import io.netty.demo.codec.MessageEncoder;
import io.netty.util.CharsetUtil;

/**
 * @author Jie Zhao
 * @date 2021/7/12 20:03
 */
public class NettyServer01 {

    public static void main(String[] args) throws InterruptedException {
        // 1、创建bossGroup线程组：处理网络事件-连接事件
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        // 2、创建workerGroup线程组：处理网络事件-读写事件
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        // 3、创建服务器启动助手
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        // 4、设置bossGroup线程组合workerGroup线程组
        serverBootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class) // 5、设置服务端通道为NIO
                .option(ChannelOption.SO_BACKLOG, 128) // 6、参数设置
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childHandler(new ChannelInitializer<SocketChannel>() { // 7、创建一个通道初始化对象
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        // 添加解码器
                        ch.pipeline().addLast("messageDecoder", new MessageDecoder());

                        // 添加编码器
                        ch.pipeline().addLast("messageEncoder", new MessageEncoder());

                        // 8、向ChannelPipeline中添加自定义的业务处理ChannelHandler
                        ch.pipeline().addLast(new NettyServerHandler());
                    }
                });
        // 9、启动服务端并绑定端口，同时将异步改为同步
        ChannelFuture future = serverBootstrap.bind(9999).sync();
        System.out.println("服务器启动成功了...");
        // 10、关闭通道和关闭连接池
        future.channel().closeFuture().sync(); // 关闭通道，并不是真正意义上的关闭，而是监听通道关闭的状态
        bossGroup.shutdownGracefully(); // 关闭连接池
        workerGroup.shutdownGracefully();
    }

    static class NettyServerHandler implements ChannelInboundHandler {

        /**
         * 通道读取事件
         *
         * @param ctx
         * @param msg
         * @throws Exception
         */
        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            // ByteBuf byteBuf = (ByteBuf) msg;
            // System.out.println("客户端消息: " + byteBuf.toString(CharsetUtil.UTF_8));
            System.out.println("客户端消息: " + msg);
            // log.info("客户端消息: {}", byteBuf.toString(CharsetUtil.UTF_8));
        }

        /**
         * 通道读取完毕事件
         *
         * @param ctx
         * @throws Exception
         */
        @Override
        public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
            // ctx.writeAndFlush(Unpooled.copiedBuffer("你好，我是netty服务端", CharsetUtil.UTF_8));
            ctx.writeAndFlush("你好，我是netty服务端");
        }

        /**
         * 通道异常事件
         *
         * @param ctx
         * @param cause
         * @throws Exception
         */
        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            // log.error("通道发送错误：", cause);
            cause.printStackTrace();
            ctx.close();
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
        public void channelActive(ChannelHandlerContext ctx) throws Exception {

        }

        @Override
        public void channelInactive(ChannelHandlerContext ctx) throws Exception {

        }

        @Override
        public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {

        }

        @Override
        public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {

        }
    }
}
