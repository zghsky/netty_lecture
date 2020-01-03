package com.shengsiyuan.nio;

import java.io.IOException;
import java.nio.channels.ServerSocketChannel;

public class NioServer {
    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        // 配置成非阻塞的
        serverSocketChannel.configureBlocking(false);
    }
}
