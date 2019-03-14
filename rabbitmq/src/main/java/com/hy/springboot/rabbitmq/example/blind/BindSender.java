package com.hy.springboot.rabbitmq.example.blind;

import com.hy.springboot.rabbitmq.api.RabbitMqFactory;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

/**
 *      direct类型交换器
 *
 *      发送消息到指定交换器上,并附带类型,方便接收端判断绑定
 *
 **/
public class BindSender {

    private static final String EXCHANGE_NAME = "direct_logs";
    private static final String[] LOG_LEVEL_ARR = {"debug", "info", "error"};

    public static void main(String[] args) throws IOException, TimeoutException {

        //建立连接
        Connection connection = RabbitMqFactory.newConnection();
        Channel channel = connection.createChannel();


        //指定交换器(特定)
        channel.exchangeDeclare(EXCHANGE_NAME, "direct");

        // 发送消息
        for (int i = 0; i < 10; i++)  {
            int rand = new Random().nextInt(3);
            String severity  = LOG_LEVEL_ARR[rand];
            String message = "yang-MSG log : [" +severity+ "]" + UUID.randomUUID().toString();
            // 发布消息至交换器
            channel.basicPublish(EXCHANGE_NAME, severity, null, message.getBytes());
            System.out.println(" Sent '" + message + "'");
        }
        // 关闭频道和连接
        channel.close();
        connection.close();

    }

}
