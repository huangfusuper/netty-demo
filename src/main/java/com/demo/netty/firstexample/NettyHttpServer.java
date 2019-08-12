package com.demo.netty.firstexample;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * 模拟Http服务器
 * @author 皇甫
 */
public class NettyHttpServer {
    public static void main(String[] args) throws InterruptedException {
        //启动服务引导 框架启动引导类 屏蔽网络通讯配置信息
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        //创建线程池组
        //boss接收服务请求 但是并不处理而是将请求委托给worker
        EventLoopGroup boss = new NioEventLoopGroup();
        //真正的处理类
        EventLoopGroup worker = new NioEventLoopGroup();
        //将委托类和执行类关联至启动类
        serverBootstrap.group(boss,worker)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ServerChannelInitializer());
        ChannelFuture sync = serverBootstrap.bind(8899).sync();
        sync.channel().closeFuture().sync();
        boss.shutdownGracefully();
        worker.shutdownGracefully();
    }

}
