package com.demo.netty.secondexample.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @author 自定义头信息  处理器
 */
public class MyClientHandler extends SimpleChannelInboundHandler<String> {
    /**
     *
     * @param ctx 上下文  核心
     * @param msg 请求对象  客户端发送过来的
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        Thread.sleep(2000);
        //获取客户端地址
        System.out.println(ctx.channel().remoteAddress() + ", " + msg);
        //向服务端发送信息
        ctx.writeAndFlush("向你发送："+ LocalDateTime.now());
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush("来自客户端的问候");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
