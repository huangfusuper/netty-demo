package com.demo.netty.secondexample;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

/**
 * 客户端和服务端发送消息 添加消息处理消息头
 * @author huangfu
 */
public class MyServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        /**
         * 他是基于长度场的帧解码器 以后会做名词讲解
         * maxFrameLength：单个包最大的长度，这个值根据实际场景而定。
         * lengthFieldOffset：表示数据长度字段开始的偏移量
         * lengthFieldLength：数据长度字段的所占的字节数
         * lengthAdjustment：lengthAdjustment + 数据长度取值 = 数据长度字段之后剩下包的字节数
         * initialBytesToStrip：表示从整个包第一个字节开始，向后忽略的字节数
         */
        pipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE,0,4,0,4));
        //长度字段预编程器
        pipeline.addLast(new LengthFieldPrepender(4));
        //解码器
        pipeline.addLast(new StringDecoder(CharsetUtil.UTF_8));
        //编码器
        pipeline.addLast(new StringEncoder(CharsetUtil.UTF_8));
        //自定义处理器
        pipeline.addLast(new MyServerHandler());
    }
}
