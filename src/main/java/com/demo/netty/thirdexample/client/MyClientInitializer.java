package com.demo.netty.thirdexample.client;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

/**
 * 聊天室客户端 的初始化器
 * @author huangfu
 */
public class MyClientInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        //根据某一字符编解码
        pipeline.addLast(new DelimiterBasedFrameDecoder(4096, Delimiters.lineDelimiter()));
        //编码器
        pipeline.addLast(new StringDecoder(CharsetUtil.UTF_8));
        //解码器
        pipeline.addLast(new StringEncoder(CharsetUtil.UTF_8));
        //自定义消息处理器
        pipeline.addLast(new MyClientHandler());
    }
}
