package io.netty.demo.example.httpserver;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import java.util.Objects;

/**
 * http服务器处理类
 * @author Jie Zhao
 * @date 2021/7/15 21:06
 */
public class NettyHttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {

    /**
     * 读取就绪事件
     * @param ctx           the {@link ChannelHandlerContext} which this {@link SimpleChannelInboundHandler}
     *                      belongs to
     * @param msg           the message to handle
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {

        // 判断请求是不是http请求
        if (msg instanceof HttpRequest) {
            HttpRequest request = (DefaultHttpRequest) msg;
            System.out.println("浏览器请求路径: " + request.uri());

            if (Objects.equals("/favicon.ico", request.uri())) {
                System.out.println("图标不响应");
                return;
            }

            // 对浏览器进行响应
            ByteBuf byteBuf = Unpooled.copiedBuffer("Hello, 我是Netty服务器", CharsetUtil.UTF_8);
            HttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, byteBuf);

            // 设置响应头
            response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/html;charset=utf-8")
                    .set(HttpHeaderNames.CONTENT_LENGTH, byteBuf.readableBytes());

            ctx.writeAndFlush(response);
        }
    }
}
