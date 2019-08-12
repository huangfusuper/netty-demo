package com.demo.netty.thirdexample;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * 聊天室服务器的处理类
 * @author huangfu
 */
public class MyServerHandler extends SimpleChannelInboundHandler<String> {
    /**
     * 存储channel对象
     */
    private static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    /**
     * 上线提醒  以及聊天提醒  相当于聊天室的群发
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        Channel channel = ctx.channel();
        //自己发的消息过滤掉自己
        channels.forEach(chn ->{
            //如果不是自己就加上ip地址和端口号
            if(channel != chn){
                chn.writeAndFlush("【"+channel.remoteAddress()+ "】发送消息为:" +msg + "\n");
            }else{
                chn.writeAndFlush("【自己】发送" + msg + "\n");
            }
        });
    }

    /**
     * 当有新的客户端加入时  广播一条消息
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        //告诉所有已经建立连接的对象  某个通道即用户上线
        channels.writeAndFlush("【服务器】 - " + channel.remoteAddress() + "加入\n");
        /**
         * 将已经了建立连接的对象加入进去
         */
        channels.add(channel);
    }

    /**
     * 连接断开
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        channels.writeAndFlush("【服务器】 - " + channel.remoteAddress() + "离开\n");
        //其实无需手动出发  一旦连接断掉  ChannelGroup 会自动调用remove()
        channels.remove(channel);
    }

    /**
     *表示连接处于活动状态
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        System.out.println("【服务器】 - " + channel.remoteAddress() + "上线");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        System.out.println("【服务器】 - " + channel.remoteAddress() + "下线");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
