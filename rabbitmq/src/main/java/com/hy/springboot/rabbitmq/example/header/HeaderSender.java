package com.hy.springboot.rabbitmq.example.header;

import com.hy.springboot.rabbitmq.api.RabbitMqFactory;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * @user yang.he
 * @date 2019/3/20
 * @introduce
 **/
public class HeaderSender {

    private static final String EXCHANGE_NAME = "header_message";

    public static void main(String[] args) throws Exception {

        Channel channel = RabbitMqFactory.getChannel();

        Map<String,Object> headerMap = new HashMap<String, Object>();
        headerMap.put("ig","1");
        headerMap.put("rng","2");
        headerMap.put("we","3");

        AMQP.BasicProperties properties = new AMQP.BasicProperties().builder().headers(headerMap).build();
        String message = "header类型方式的消息";

        channel.basicPublish(EXCHANGE_NAME,"",properties,message.getBytes());

//        channel.close();
    }

}
