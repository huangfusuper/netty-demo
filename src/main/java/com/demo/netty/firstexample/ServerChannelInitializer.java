package com.demo.netty.firstexample;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * 自定义的处理器
 * 其实他也是一个委托类 再次将请求委托给另外一个类
 * 它本身只做一个初始化的操作 添加各种Handler
 * @author 皇甫
 */
public class ServerChannelInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        //创建个管道的套接字
        ChannelPipeline pipeline = ch.pipeline();
        //将请求委托给真正的最终处理类  自定义
        pipeline.addLast(new HttpServerCodec());
        pipeline.addLast(new ServerChannelHandlerAdapter());
    }
}
