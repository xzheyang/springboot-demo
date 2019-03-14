package com.hy.springboot.rabbitmq.api;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @user yang.he
 * @date 2019/3/8
 * @introduce
 **/
public class RabbitMqFactory {

    //创建通道
    public static Channel getChannel() throws IOException, TimeoutException {

        Connection connection = newConnection();

        return connection.createChannel();
    }

    //创建连接
    public static Connection newConnection() throws IOException, TimeoutException {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setUsername("admin");
        factory.setPassword("admin");
        factory.setHost("172.16.1.177");

        return factory.newConnection();
    }

}
