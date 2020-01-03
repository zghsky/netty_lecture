package com.shengsiyuan.nio;

import java.nio.ByteBuffer;

/**
 * 只读 Buffer
 */
public class NioTest7 {

    public static void main(String[] args) {

        ByteBuffer buffer = ByteBuffer.allocate(10);

        System.out.println(buffer.getClass());

        for (int i = 0; i < buffer.capacity(); i++) {
            buffer.put((byte) i);
        }

        ByteBuffer read = buffer.asReadOnlyBuffer();

        System.out.println(read.getClass());
    }
}
