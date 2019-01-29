package com.xhh.wechatimsystem.wechatimsystem.nettyBasic;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;

/**
 * netty服务端
 *
 * netty定义：
 * Netty封装了JDK的NIO，让你用得更爽，你不用再写一大堆复杂的代码了。
 * Netty是一个异步事件驱动的网络应用框架，用于快速开发可维护的高性能服务器和客户端。
 *
 * 优势：
 * 使用JDK自带的NIO需要了解太多的概念，编程复杂，一不小心bug横飞
 * Netty底层IO模型随意切换，而这一切只需要做微小的改动，改改参数，Netty可以直接从NIO模型变身为IO模型
 * Netty自带的拆包解包，异常检测等机制让你从NIO的繁重细节中脱离出来，让你只需要关心业务逻辑
 * Netty解决了JDK的很多包括空轮询在内的bug
 * Netty底层对线程，selector做了很多细小的优化，精心设计的reactor线程模型做到非常高效的并发处理
 * 自带各种协议栈让你处理任何一种通用协议都几乎不用亲自动手
 * Netty社区活跃，遇到问题随时邮件列表或者issue
 * Netty已经历各大rpc框架，消息中间件，分布式通信中间件线上的广泛验证，健壮性无比强
 *
 * luokai
 * 2019/1/29 0029 下午 3:46
 */

public class NettyServer {
    public static void main(String[] args) {
        //引导类 ServerBootstrap，引导进行服务端的启动
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        //boos表示监听端口，创建新连接的线程组
        NioEventLoopGroup boos = new NioEventLoopGroup();
        //worker表示处理每一条连接的数据读写的线程组
        NioEventLoopGroup worker = new NioEventLoopGroup();
        serverBootstrap
                .group(boos, worker)//引导类配置两大线程
                .channel(NioServerSocketChannel.class) //指定服务端的IO模型为NIO
                .childHandler(new ChannelInitializer<NioSocketChannel>() {//定义后续每条连接的数据读写，业务处理逻辑
                    @Override
                    protected void initChannel(NioSocketChannel ch) {
                        ch.pipeline().addLast(new StringDecoder());
                        ch.pipeline().addLast(new SimpleChannelInboundHandler<String>() {
                            @Override
                            protected void channelRead0(ChannelHandlerContext ctx, String msg) {
                                System.out.println(msg);
                            }
                        });
                    }
                })
                .bind(8000);
    }
}