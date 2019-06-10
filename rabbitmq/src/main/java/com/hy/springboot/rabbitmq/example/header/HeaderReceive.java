package com.hy.springboot.rabbitmq.example.header;

import com.hy.springboot.rabbitmq.api.RabbitMqFactory;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * @user yang.he
 * @date 2019/3/20
 * @introduce
 **/
public class HeaderReceive {

    private static final String EXCHANGE_NAME = "header_message";

    public static void main(String[] args) throws Exception {

        Channel channel = RabbitMqFactory.getChannel();

        Map<String,Object> headerMap = new HashMap<String, Object>();
        headerMap.put("x-match","any");
        headerMap.put("ig","1");

        String queueName = channel.queueDeclare().getQueue();
        channel.exchangeDeclare(EXCHANGE_NAME,"headers");
        channel.queueBind(queueName,EXCHANGE_NAME,"",headerMap);

        Consumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("receive = [" + new String(body,"UTF-8") + "]");
            }
        };

        channel.basicConsume(queueName,consumer);

    }

}
