package com.shengsiyuan.netty.sixthexample;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Random;

public class TestClientHandler extends SimpleChannelInboundHandler<MyDataInfo.MyMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MyDataInfo.MyMessage msg) throws Exception {

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        int random = new Random().nextInt(3);

        MyDataInfo.MyMessage message = null;
        if (random == 0) {
            message = MyDataInfo.MyMessage.newBuilder().setDataType(
                    MyDataInfo.MyMessage.DataType.PersonType).setPerson(MyDataInfo.Person.
                    newBuilder().setName("胡啸").setAge(250).setAddress("桐城").build()).build();
        } else if (random == 1) {
            message = MyDataInfo.MyMessage.newBuilder().setDataType(
                    MyDataInfo.MyMessage.DataType.DogType).setDog(MyDataInfo.Dog.
                    newBuilder().setName("一只胡啸").setAge(250).build()).build();
        } else {
            message = MyDataInfo.MyMessage.newBuilder().setDataType(
                    MyDataInfo.MyMessage.DataType.CatType).setCat(MyDataInfo.Cat.
                    newBuilder().setName("一只胡啸喵").setCity("桐城喵").build()).build();
        }

        ctx.channel().writeAndFlush(message);
    }
}
