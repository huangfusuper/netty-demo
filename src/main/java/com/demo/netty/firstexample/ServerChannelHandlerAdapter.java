package com.demo.netty.firstexample;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

/**
 * 处理器最终处理类
 * @author huangfu
 */
public class ServerChannelHandlerAdapter extends SimpleChannelInboundHandler<HttpObject> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
        if(msg instanceof HttpRequest){
            HttpRequest httpRequest = (HttpRequest)msg;
            System.out.println("-------------------------方法名"+httpRequest.method().name()+"-------------------------");
            ByteBuf byteBuf = Unpooled.copiedBuffer("Hello World", CharsetUtil.UTF_8);
            //HTTP响应
            FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK,byteBuf);
            //设置头信息
            response.headers().set(HttpHeaderNames.CONTENT_TYPE,"text/plain");
            //响应给客户端
            response.headers().set(HttpHeaderNames.CONTENT_LENGTH,byteBuf.readableBytes());
            ctx.writeAndFlush(response);
        }
    }

    //通道注册成功
    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("-------------------------通道开始注册--------------------------------");
        super.channelRegistered(ctx);
    }

    /**
     * 自定义的Handler被添加,也就是在ServerChannelInitializer的initChannel方法中，
     * pipeline.addLast("ServerChannelHandlerAdapter", new TestHttpServerHandler());
     * 这行代码执行的时候，该方法被触发
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        System.out.println("---------------------通道被添加--------------------");
        super.handlerAdded(ctx);
    }

    //通道处于活动状态，即可用状态
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("-----------------通道处于活动状态---------------------");
        super.channelActive(ctx);
    }

    //通道处于不活动状态
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("----------------------通道处于不活动的状态--------------------");
        super.channelInactive(ctx);
    }

    //通道取消注册
    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("----------------------通道释放----------------------");
        super.channelUnregistered(ctx);
    }

    /**
     * 异常捕获
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
