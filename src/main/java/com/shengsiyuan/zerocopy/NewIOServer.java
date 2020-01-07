package com.shengsiyuan.zerocopy;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class NewIOServer {

    public static void main(String[] args) throws IOException {

        InetSocketAddress address = new InetSocketAddress(8899);

        ServerSocketChannel server = ServerSocketChannel.open();
        ServerSocket socket = server.socket();
        // 让地址处于超时情况下可再用, bind 之前调用此方法 bind之后调用是行为未定义的
        socket.setReuseAddress(true);
        socket.bind(address);

        ByteBuffer buffer = ByteBuffer.allocate(1024 * 4);

        while (true) {
            SocketChannel channel = server.accept();
            channel.configureBlocking(true);

            int len = 0;

            while ((len = channel.read(buffer)) != -1) {
                buffer.rewind();
            }
        }
    }
}
