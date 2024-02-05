package io.netty.buffer;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.netty.buffer.ByteBufUtil.appendPrettyHexDump;
import static io.netty.util.internal.StringUtil.NEWLINE;

/**
 * @author Jie Zhao
 * @date 2024/2/4 21:02
 */
@Slf4j
public class ByteBufTest {

    @Test
    @DisplayName("测试创建ByteBuf")
    public void test() {
        ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer();

        log(buffer);
        buffer.writeByte(11);
        log(buffer);

        System.out.println("Hello ByteBuf");
    }

    @Test
    public void testSlice() {
        ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer();
        // todo
        buffer.slice();
    }

    public static void log(ByteBuf buffer) {
        int length = buffer.readableBytes();
        int rows = length / 16 + (length % 15 == 0? 0:1) + 4;
        StringBuilder sb = new StringBuilder(rows * 80 * 2)
                .append("read index:").append(buffer.readerIndex())
                .append(" write index:").append(buffer.writerIndex())
                .append(" capacity:").append(buffer.capacity())
                .append(NEWLINE);
        appendPrettyHexDump(sb, buffer);
        System.out.println(sb);
    }
}
