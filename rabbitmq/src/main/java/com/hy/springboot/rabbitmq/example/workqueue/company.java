package com.hy.springboot.rabbitmq.example.workqueue;

import com.hy.springboot.rabbitmq.api.RabbitMqFactory;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author yang.he
 *
 *  工作队列发送者
 *
 **/
public class company {

    private final static String QUEUE_NAME = "work";

    public static void main(String[] args) throws IOException, TimeoutException {

        // 创建连接
        Connection connection = RabbitMqFactory.newConnection();
        // 创建一个通道
        Channel channel = connection.createChannel();
        // 指定一个队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        /*
                公平转发,默认是当前有几个消费者连接就平均分配,
                现在是在处理并确认前一个消息之前，不向工作线程发送新消息,并发给不忙的线程.
          */
//        int prefetchCount = 1;
//        channel.basicQos(prefetchCount);

        // 发送消息
        for (int i = 1; i <= 6; i++) {
            String message = "发送信息:" + i;
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
            System.out.println(" Sent '" + message + "'");
        }
        // 关闭频道和连接
        channel.close();
        connection.close();
    }

}
