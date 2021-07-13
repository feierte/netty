package io.netty.demo.example.chatroom;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * 聊天室服务端
 * @author Jie Zhao
 * @date 2021/7/13 21:01
 */
public class ChatServer {

    private int port;

    public ChatServer(int port) {
        this.port = port;
    }

    public void run() throws InterruptedException {
        // 1、创建bossGroup线程组：处理网络事件-连接事件
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        // 2、创建workerGroup线程组：处理网络事件-读写事件
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
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
                            ch.pipeline().addLast(new StringDecoder());
                            // 添加编码器
                            ch.pipeline().addLast(new StringEncoder());

                            // 8、向ChannelPipeline中添加自定义的业务处理ChannelHandler
                            ch.pipeline().addLast(new CharServerHandler());
                        }
                    });
            // 9、启动服务端并绑定端口，同时将异步改为同步
            ChannelFuture future = serverBootstrap.bind(port).sync();
            System.out.println("聊天室服务器启动成功了...");
            // 10、关闭通道和关闭连接池
            future.channel().closeFuture().sync(); // 关闭通道，并不是真正意义上的关闭，而是监听通道关闭的状态
        } finally {
            bossGroup.shutdownGracefully(); // 关闭连接池
            workerGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ChatServer chatRoomServer = new ChatServer(9999);
        chatRoomServer.run();
    }
}
