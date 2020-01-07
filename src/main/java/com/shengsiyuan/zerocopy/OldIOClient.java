package com.shengsiyuan.zerocopy;

import java.io.*;
import java.net.Socket;

public class OldIOClient {

    public static void main(String[] args) throws IOException {

        Socket socket = new Socket("localhost", 8899);

        String path = "D:/安装包/jdk-8u231-windows-x64.exe";

        InputStream inputStream = new FileInputStream(new File(path));

        DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());

        long startTime = System.currentTimeMillis();

        byte[] bytes = new byte[1024 * 4];
        int len = 0;
        int total = 0;

        for (; (len = inputStream.read(bytes)) != -1;) {
            total += len;
            outputStream.write(bytes, 0, len);
        }

        System.out.println("发送总字节数: " + total + ", 耗时: " +(System.currentTimeMillis() - startTime));

        outputStream.close();
        inputStream.close();
        socket.close();
    }
}
