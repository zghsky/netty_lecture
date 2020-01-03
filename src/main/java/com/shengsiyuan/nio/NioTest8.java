package com.shengsiyuan.nio;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NioTest8 {

    public static void main(String[] args) throws IOException {

        FileInputStream inputStream = new FileInputStream("input2.txt");
        FileOutputStream outputStream = new FileOutputStream("output2.txt");

        FileChannel inputChannel = inputStream.getChannel();
        FileChannel outputChannel = outputStream.getChannel();

        ByteBuffer buffer = ByteBuffer.allocate(512);

        while (true) {
            int read = inputChannel.read(buffer);

            if (read == -1) break;

            buffer.flip();

            outputChannel.write(buffer);
        }

        outputStream.close();
        inputChannel.close();
    }
}
