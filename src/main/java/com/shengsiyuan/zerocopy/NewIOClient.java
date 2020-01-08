package com.shengsiyuan.zerocopy;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

public class NewIOClient {

    public static void main(String[] args) throws IOException {

        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("localhost", 8899));
        socketChannel.configureBlocking(true);

        long startTime = System.currentTimeMillis();
        String path = "D:/安装包/jdk-8u231-windows-x64.exe";
        File file = new File(path);
        FileChannel channel = new FileInputStream(file).getChannel();

        long transferToCount = 0;
        long transferTo = 0;
        for (int i = 0; i < file.length(); i += transferTo) {
            transferTo = channel.transferTo(transferToCount, channel.size(), socketChannel);
            transferToCount += transferTo;
        }

        System.out.println("发送总字节数: " + transferToCount + ", 耗时: " +(System.currentTimeMillis() - startTime));
        channel.close();
    }
}
