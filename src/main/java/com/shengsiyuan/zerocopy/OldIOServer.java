package com.shengsiyuan.zerocopy;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class OldIOServer {

    public static void main(String[] args) throws IOException {

        ServerSocket server = new ServerSocket(8899);

        while (true) {
            Socket socket = server.accept();
            DataInputStream inputStream = new DataInputStream(socket.getInputStream());

            byte[] bytes = new byte[1024 * 4];
            int read = 0;
            for (; (read = inputStream.read(bytes)) != -1; );

            inputStream.close();
        }
    }
}
