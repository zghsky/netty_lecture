package com.shengsiyuan.nio;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NioTest3 {

    public static void main(String[] args) throws IOException {

        FileOutputStream fileOutputStream = new FileOutputStream("NioTest3.txt");
        FileChannel channel = fileOutputStream.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate(512);
        byte[] bytes = "Hello Nio".getBytes();
        for (byte aByte : bytes) {
            byteBuffer.put(aByte);
        }

        byteBuffer.flip();
        channel.write(byteBuffer);

        fileOutputStream.close();
    }
}
