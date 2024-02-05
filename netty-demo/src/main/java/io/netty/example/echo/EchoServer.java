package io.netty.example.echo;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Jie Zhao
 * @date 2024/2/5 12:17
 */
public class EchoServer {

    private static final Logger log = LoggerFactory.getLogger(EchoServer.class);

    public static void main(String[] args) {
        // 启动器，负责组装 netty 组件，启动服务端程序
        ServerBootstrap server = new ServerBootstrap();
        // Boss EventLoopGroup
        server.group(new NioEventLoopGroup())
                // 选择服务器的 ServerSocketChannel 实现
                .channel(NioServerSocketChannel.class)
                // boss 负责处理连接，worker（child）负责处理读写
                // 这里就是添加处理读写请求的 handler
                .childHandler(
                        // Channel 代表和客户端进行读写的通道
                        // ChannelInitializer Channel初始化器，为 Channel 添加不同的 handler 等其他操作。
                        new ChannelInitializer<NioSocketChannel>() {
                            @Override
                            protected void initChannel(NioSocketChannel ch) throws Exception {
                                ch.pipeline().addLast(new StringDecoder());
                                ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                                    @Override
                                    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                        if (msg instanceof ByteBuf) {
                                            msg = ((ByteBuf) msg).toString();
                                        }
                                        System.out.println(msg);
                                        log.info("服务器收到信息：{}", msg);
                                    }
                                });

                                ch.pipeline().addLast(new ChannelOutboundHandlerAdapter() {
                                    @Override
                                    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
                                        ctx.writeAndFlush(msg);
                                    }
                                });
                            }
                        })
                .bind(8888);
    }
}
