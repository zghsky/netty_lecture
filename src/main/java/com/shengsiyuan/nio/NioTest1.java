package com.shengsiyuan.nio;

import java.nio.IntBuffer;
import java.security.SecureRandom;

public class NioTest1 {

    public static void main(String[] args) {
        IntBuffer buffer = IntBuffer.allocate(10);

        for (int i = 0; i < 5; i++) {
            buffer.put(new SecureRandom().nextInt(20));
        }

        System.out.println("before flip limit: " + buffer.limit());

        buffer.flip();

        System.out.println("after flip limit: " + buffer.limit());

        while (buffer.hasRemaining()) {
            System.out.println("position: " + buffer.position());
            System.out.println("limit: " + buffer.limit());
            System.out.println("capacity: " + buffer.capacity());

            System.out.println(buffer.get());
        }
    }
}
