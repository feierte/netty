package io.netty.demo.example.chatroom;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.demo.codec.MessageDecoder;
import io.netty.demo.codec.MessageEncoder;

import java.util.Scanner;

/**
 * 聊天室客户端
 * @author Jie Zhao
 * @date 2021/7/15 20:47
 */
public class ChatClient {

    private String ip;
    private int port;

    public ChatClient(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    public void run() throws InterruptedException {
        // 1、创建线程组
        EventLoopGroup group = new NioEventLoopGroup();
        // 2、创建客户端启动助手
        Bootstrap bootstrap = new Bootstrap();
        try {

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
                            ch.pipeline().addLast(new ChatClientHandler());
                        }
                    });

            // 7、启动客户端，等待连接服务器，同时将异步改为同步
            ChannelFuture channelFuture = bootstrap.connect(ip, port).sync();
            Channel channel = channelFuture.channel();
            System.out.println("-------------"+ channel.localAddress().toString().substring(1) +"-------------");
            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNextLine()) {
                String msg = scanner.nextLine();
                // 向服务端发送消息
                channel.writeAndFlush(msg);
            }
            // 8、关闭通道和关闭连接池
            channelFuture.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ChatClient client = new ChatClient("localhost", 9999);
        client.run();
    }
}
