package com.shengsiyuan.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class NioServer {

    private static Map<String, SocketChannel> clientMap = new HashMap<>();

    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        // 配置成非阻塞的
        serverSocketChannel.configureBlocking(false);
        ServerSocket socket = serverSocketChannel.socket();
        socket.bind(new InetSocketAddress(8899));

        Selector selector = Selector.open();
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        while (true) {

            try {
                selector.select();
                Set<SelectionKey> selectionKeys = selector.selectedKeys();

                selectionKeys.forEach(selectionKey -> {
                    final SocketChannel client;

                    try {
                        if (selectionKey.isAcceptable()) {
                            ServerSocketChannel channel = (ServerSocketChannel) selectionKey.channel();
                            client = channel.accept();
                            client.configureBlocking(false);
                            client.register(selector, SelectionKey.OP_READ);

                            String key = UUID.randomUUID().toString();
                            clientMap.put(key, client);
                        } else if (selectionKey.isReadable()) {
                            client = (SocketChannel) selectionKey.channel();
                            ByteBuffer readBuffer = ByteBuffer.allocate(512);
                            int count = client.read(readBuffer);

                            if (count > 0) {
                                readBuffer.flip();
                                Charset charset = StandardCharsets.UTF_8;
                                String message = String.valueOf(charset.decode(readBuffer).array());

                                System.out.println(client + ": " + message);

                                String getKey = null;
                                for (Map.Entry<String, SocketChannel> stringEntry : clientMap.entrySet()) {
                                    if (stringEntry.getValue() == client) {
                                        getKey = stringEntry.getKey();
                                    }
                                }

                                for (Map.Entry<String, SocketChannel> channelEntry : clientMap.entrySet()) {
                                    SocketChannel value = channelEntry.getValue();
                                    ByteBuffer writeBuffer = ByteBuffer.allocate(512);
                                    writeBuffer.put((value + ": " + message).getBytes());
                                    writeBuffer.flip();
                                    value.write(writeBuffer);
                                }
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    selectionKeys.remove(selectionKey);
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}