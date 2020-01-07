package com.shengsiyuan.nio;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.StandardCharsets;

public class NioTest13 {
    public static void main(String[] args) throws IOException {

        String inputFile = "NioTest13_in.txt";
        String outputFile = "NioTest13_out.txt";

        RandomAccessFile inputAccessFile = new RandomAccessFile(inputFile, "r");
        RandomAccessFile outputAccessFile = new RandomAccessFile(outputFile, "rw");

        long length = new File(inputFile).length();

        FileChannel inputChannel = inputAccessFile.getChannel();
        FileChannel outputChannel = outputAccessFile.getChannel();

        // 内存映射缓冲 MappedByteBuffer
        MappedByteBuffer inputData = inputChannel.map(FileChannel.MapMode.READ_ONLY, 0, length);

        System.out.println("=======================");

        Charset.availableCharsets().forEach((k, v) ->{
            System.out.println(k + ", " + v);
        });

        System.out.println("=======================");

        Charset charset = StandardCharsets.UTF_8;

        //解码：将磁盘上文件解析成字符或字符串
        CharsetDecoder charsetDecoder = charset.newDecoder();
        //编码：转换成字节或字节数组
        CharsetEncoder charsetEncoder = charset.newEncoder();

        CharBuffer charBuffer = charsetDecoder.decode(inputData);
        ByteBuffer outputData = charsetEncoder.encode(charBuffer);

        outputChannel.write(outputData);

        inputAccessFile.close();
        outputAccessFile.close();
    }
}
