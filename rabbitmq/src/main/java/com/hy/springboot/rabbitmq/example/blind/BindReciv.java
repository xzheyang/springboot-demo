package com.hy.springboot.rabbitmq.example.blind;

import com.hy.springboot.rabbitmq.api.RabbitMqFactory;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 *      direct类型交换器
 *
 *   创建一个马上删除的消息队列,指定交换器,并接收自己指定类型的数据.
 *
 **/
public class BindReciv {

    private static final String EXCHANGE_NAME = "direct_logs";
    private static final String[] LOG_LEVEL_ARR = {"debug", "info", "error"};


    public static void main(String[] args) throws IOException, TimeoutException {

        //建立连接
        Connection connection = RabbitMqFactory.newConnection();
        Channel channel = connection.createChannel();


        // 创建一个非持久的、唯一的、自动删除的队列
        String queueName = channel.queueDeclare().getQueue();

        //指定交换器(direct)
        channel.exchangeDeclare(EXCHANGE_NAME, "direct");
        //FIXME 这个是不是包含上面(不包含,必须先指定交换器 )
        //绑定队列指定队列的指定类型(这里是error类型,方法可以多次使用,绑定多个)
        channel.queueBind(queueName, EXCHANGE_NAME, LOG_LEVEL_ARR[2]);
        channel.queueBind(queueName, EXCHANGE_NAME, LOG_LEVEL_ARR[1]);


        // 创建队列消费者
        final Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println(" Received '" + message + "'");
            }
        };
        channel.basicConsume(queueName, true, consumer);


    }

}
