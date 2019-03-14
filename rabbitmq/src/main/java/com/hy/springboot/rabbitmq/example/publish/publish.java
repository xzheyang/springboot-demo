package com.hy.springboot.rabbitmq.example.publish;

import com.hy.springboot.rabbitmq.api.RabbitMqFactory;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 *    fanout类型交换器发送全部
 *    指定交换器,发布消息,让多个消费者处理同一个消息
 **/
public class publish {

    private static final String EXCHANGE_NAME = "logs";

    public static void main(String[] args) throws IOException, TimeoutException {
        // 创建连接
        Connection connection = RabbitMqFactory.newConnection();
        Channel channel = connection.createChannel();


        // 指定一个交换器
        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
        // 发送消息
        String message = "song-MSG log.";
        channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes());
        System.out.println(" Sent '" + message + "'");

        // 关闭频道和连接
        channel.close();
        connection.close();
    }


}
