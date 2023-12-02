package io.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.EmptyByteBuf;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Jie Zhao
 * @date 2023/10/14 上午10:32
 */
@Slf4j
public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        ByteBuf byteBuf = new EmptyByteBuf(ByteBufAllocator.DEFAULT);
        System.out.println(byteBuf);
        log.info("Hello World");
    }
}