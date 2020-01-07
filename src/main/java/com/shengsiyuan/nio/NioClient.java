package com.shengsiyuan.nio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NioClient {

    public static void main(String[] args) {

        try {
            SocketChannel socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(false);

            Selector selector = Selector.open();
            socketChannel.register(selector, SelectionKey.OP_CONNECT);
            socketChannel.connect(new InetSocketAddress("127.0.0.1", 8899));

            while (true) {
                selector.select();
                Set<SelectionKey> selectionKeys = selector.selectedKeys();

                for (SelectionKey selectionKey : selectionKeys) {
                    if (selectionKey.isConnectable()) {
                        SocketChannel client = (SocketChannel) selectionKey.channel();

                        if (client.isConnectionPending()) {
                            client.finishConnect();

                            ByteBuffer writeBuffer = ByteBuffer.allocate(512);
                            writeBuffer.put((LocalDateTime.now() + "连接成功").getBytes());
                            writeBuffer.flip();
                            client.write(writeBuffer);

                            ExecutorService executorService = Executors.newSingleThreadExecutor(Executors.defaultThreadFactory());
                            executorService.submit(() ->{
                                while (true) {
                                    writeBuffer.clear();

                                    InputStreamReader input = new InputStreamReader(System.in);
                                    BufferedReader br = new BufferedReader(input);
                                    try {
                                        String s = br.readLine();
                                        writeBuffer.put(s.getBytes());
                                        writeBuffer.flip();

                                        client.write(writeBuffer);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        }

                        client.register(selector, SelectionKey.OP_READ);
                    } else if (selectionKey.isReadable()) {
                        SocketChannel client = (SocketChannel) selectionKey.channel();

                        ByteBuffer buffer = ByteBuffer.allocate(512);
                        int count = client.read(buffer);

                        if (count > 0) {
                            System.out.println(new String(buffer.array(), 0, count));
                        }
                    }
                    selectionKeys.remove(selectionKey);
                }
                selectionKeys.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
