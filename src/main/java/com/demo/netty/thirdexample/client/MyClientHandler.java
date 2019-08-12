package com.demo.netty.thirdexample.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * 客户端对聊天室消息的自定义处理
 * @author huangfu
 */
public class MyClientHandler extends SimpleChannelInboundHandler<String> {
    /**
     * 客户端的一个消息处理器
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        //就显示出来另外客户端发送过来的消息 显示在控制台
        System.out.println(msg);
    }
}
