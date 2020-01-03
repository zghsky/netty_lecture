package com.shengsiyuan.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NioTest4 {
    public static void main(String[] args) throws IOException {

        FileInputStream fileInputStream = new FileInputStream("input.txt");
        FileOutputStream fileOutputStream = new FileOutputStream("output.txt");

        FileChannel inputChannel = fileInputStream.getChannel();
        FileChannel outputChannel = fileOutputStream.getChannel();

        // 堆外内存的概念 其调用了操作系统内部分配内存 减少IO的Java堆内存到堆外内存一次拷贝
        ByteBuffer buffer = ByteBuffer.allocateDirect(512);

        while (true) {
            buffer.clear();
            int read = inputChannel.read(buffer);
            System.out.println("read: " + read);

            if (read == -1) break;
            buffer.flip();

            outputChannel.write(buffer);
        }
        inputChannel.close();
        outputChannel.close();
    }
}
