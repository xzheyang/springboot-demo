package com.hy.springboot.rabbitmq.example.rpc;

import com.hy.springboot.rabbitmq.api.RabbitMqFactory;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

/**
 * @user yang.he
 * @date 2019/3/18
 * @introduce
 **/
public class RpcClient {

    private static final String RPC_QUEUE = "rpc_queue";


    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {

        Channel channel = RabbitMqFactory.getChannel();


        String replyQueue = channel.queueDeclare().getQueue();
        final String correlationId = UUID.randomUUID().toString();

        // 预先定义响应的结果，即预先订阅响应结果的队列，先订阅响应队列，再发送消息到请求队列
        Consumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                if (properties.getCorrelationId().equals(correlationId)){
                    String message = new String(body, "UTF-8");
                    System.out.println("获得服务器的结果:   " + message);
                }

            }
        };

        channel.basicConsume(replyQueue,true,consumer);

        //发送信息
        String message = "Hello RabbitMQ";
        AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder().correlationId(correlationId).replyTo(replyQueue).build();
        channel.basicPublish("", RPC_QUEUE, properties, message.getBytes("UTF-8"));
        System.out.println("已发出请求请求消息：" + message);
        Thread.sleep(100000);


    }

}
