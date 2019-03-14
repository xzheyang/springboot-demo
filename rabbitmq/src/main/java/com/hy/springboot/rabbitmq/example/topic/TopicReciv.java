package com.hy.springboot.rabbitmq.example.topic;

import com.hy.springboot.rabbitmq.api.RabbitMqFactory;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 *  topic类型交换器
 *
 *   使用通配符指定交换器中要接收的类型
 *   #会不管.匹配所有,而*会因为有.匹配不到
 *
 **/
public class TopicReciv {


    private static final String EXCHANGE_NAME = "topic_logs";
    private static final String[] LOG_LEVEL_ARR =
            {"#", "dao.error", "*.error", "#.error", "dao.*", "service.#", "*.controller.#"};

    public static void main(String[] args) throws IOException, TimeoutException {

        //建立连接
        Connection connection = RabbitMqFactory.newConnection();
        Channel channel = connection.createChannel();

        // 创建一个非持久的、唯一的、自动删除的队列
        String queueName = channel.queueDeclare().getQueue();
        //指定交换器(com.hy.springboot.topic)
        channel.exchangeDeclare(EXCHANGE_NAME, "com/hy/springboot/rabbitmq/example/topic");
        //绑定队列指定队列的指定类型(匹配所有error类型,方法可以多次使用,绑定多个)
        channel.queueBind(queueName, EXCHANGE_NAME, LOG_LEVEL_ARR[3]);

        // 创建队列消费者
        final Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
                                       byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println(" Received '" + message + "'");
            }
        };
        //消费者执行
        channel.basicConsume(queueName, true, consumer);


    }

}
