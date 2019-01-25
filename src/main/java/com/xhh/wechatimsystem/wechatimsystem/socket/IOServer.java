package com.xhh.wechatimsystem.wechatimsystem.socket;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * socket服务端
 * 1）线程资源受限：线程是操作系统中非常宝贵的资源，同一时刻有大量的线程处于阻塞状态是非常严重的资源浪费，操作系统耗不起
 * 2）线程切换效率低下：单机cpu核数固定，线程爆炸之后操作系统频繁进行线程切换，应用性能急剧下降。
 * 3）除了以上两个问题，IO编程中，我们看到数据读写是以字节流为单位，效率不高。
 * luokai
 * 2019/1/25 0025 下午 5:05
 */
public class IOServer {
    public static void main(String[] args) throws Exception {

        ServerSocket serverSocket = new ServerSocket(8000);

        // (1) 接收新连接线程
        new Thread(() -> {
            while (true) {
                try {
                    // (1) 阻塞方法获取新的连接
                    Socket socket = serverSocket.accept();

                    // (2) 每一个新的连接都创建一个线程，负责读取数据
                    new Thread(() -> {
                        try {
                            byte[] data = new byte[1024];
                            InputStream inputStream = socket.getInputStream();
                            while (true) {
                                int len;
                                // (3) 按字节流方式读取数据
                                while ((len = inputStream.read(data)) != -1) {
                                    System.out.println(new String(data, 0, len));
                                }
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }).start();

                } catch (IOException e) {
                }

            }
        }).start();
    }
}