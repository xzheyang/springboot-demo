package com.hy.springboot.rabbitmq.example.topic;

import com.hy.springboot.rabbitmq.api.RabbitMqFactory;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

/**
 *   topic类型交换器
 *
 *   可以使用统配符绑定信息
 *
 **/
public class TopicSender {

    private static final String EXCHANGE_NAME = "topic_logs";
    private static final String[] LOG_LEVEL_ARR = {"dao.debug", "dao.info", "dao.error",
            "service.debug", "service.info", "service.error","test.service.error",
            "controller.debug", "controller.info", "controller.error"};


    public static void main(String[] args) throws IOException, TimeoutException {

        //建立连接
        Connection connection = RabbitMqFactory.newConnection();
        Channel channel = connection.createChannel();

        // 指定一个topic类型的交换器
        channel.exchangeDeclare(EXCHANGE_NAME, "com/hy/springboot/rabbitmq/example/topic");


        // 发送消息
        for (String severity : LOG_LEVEL_ARR) {
            String message = "Yang-MSG log : [" +severity+ "]" + UUID.randomUUID().toString();
            // 发布消息至交换器
            channel.basicPublish(EXCHANGE_NAME, severity, null, message.getBytes());
            System.out.println(" Sent '" + message + "'");
        }

        // 关闭频道和连接
        channel.close();
        connection.close();

    }


}
