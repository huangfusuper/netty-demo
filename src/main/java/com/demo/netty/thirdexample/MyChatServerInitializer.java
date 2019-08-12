package com.demo.netty.thirdexample;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

/**
 * 初始化器
 * @author huangfu
 */
public class MyChatServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        //根据某一字符编解码
        pipeline.addLast(new DelimiterBasedFrameDecoder(4096, Delimiters.lineDelimiter()));
        //解码方式
        pipeline.addLast(new StringDecoder(CharsetUtil.UTF_8));
        //编码方式
        pipeline.addLast(new StringEncoder(CharsetUtil.UTF_8));
        //自定义聊天室的处理类
        pipeline.addLast(new MyServerHandler());
    }
}
