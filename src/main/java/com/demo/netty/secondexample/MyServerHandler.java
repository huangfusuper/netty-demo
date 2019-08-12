package com.demo.netty.secondexample;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.UUID;

/**
 * @author 自定义头信息  处理器
 */
public class MyServerHandler extends SimpleChannelInboundHandler<String> {
    /**
     *
     * @param ctx 上下文  核心
     * @param msg 请求对象  客户端发送过来的
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        Thread.sleep(2000);
        //获取客户端地址  打印 客户端发送的请求带上地址
        System.out.println(ctx.channel().remoteAddress() + ", " + msg);
        //将客户端发送的消息加上UUID重新发给客户端
        ctx.channel().writeAndFlush("你的请求我收到了"+ UUID.randomUUID().toString()+"消息为:"+msg);
    }

    /**
     * 出现异常会调用这个
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
