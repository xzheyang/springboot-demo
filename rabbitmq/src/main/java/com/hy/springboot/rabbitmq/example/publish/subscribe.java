package com.hy.springboot.rabbitmq.example.publish;


import com.hy.springboot.rabbitmq.api.RabbitMqFactory;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 *
 *   fanout类型交换器接收全部
 *
 *   接收指定交换器的消息
 **/
public class subscribe {

    private static final String EXCHANGE_NAME = "logs";

    public static void main(String[] args) throws IOException, TimeoutException {

        Connection connection = RabbitMqFactory.newConnection();
        Channel channel = connection.createChannel();

        // 指定一个交换器  //fanout为交换器类型
        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
        // 创建一个非持久的、唯一的、自动删除的队列
        String queueName = channel.queueDeclare().getQueue();

        /*
                绑定交换器和队列,
         */
        // 参数1 queue ：队列名
        // 参数2 exchange ：交换器名
        // 参数3 routingKey ：路由键名,只接收这个级别的消息
        channel.queueBind(queueName, EXCHANGE_NAME, "");


        // 创建队列消费者
        final Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
                                       byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println(" Received '" + message + "'");
            }
        };
        channel.basicConsume(queueName, true, consumer);
    }


}
