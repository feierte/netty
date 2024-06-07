package io.netty.nio;

import java.nio.ByteBuffer;

/**
 * @author Jie Zhao
 * @date 2024/6/7 22:03
 */
public class BufferUtils {


    public static void unpack(ByteBuffer buffer, char split) {
        buffer.flip(); //   切换为读模式

    }
}
