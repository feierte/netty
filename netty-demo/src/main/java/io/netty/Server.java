package io.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author Jie Zhao
 * @date 2024/2/3 21:21
 */
public class Server {

    public static void main(String[] args) {
        ServerBootstrap server = new ServerBootstrap();
        server.group(new NioEventLoopGroup())
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {

                    }
                })
                .bind(8888);
    }
}
