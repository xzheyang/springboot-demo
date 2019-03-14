package com.hy.springboot.rabbitmq.example.workqueue;

import com.hy.springboot.rabbitmq.api.RabbitMqFactory;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;


/**
 * @author yang.he
 *
 * 工作队列接收者
 *
 * 和某些机制
 *
 **/
public class worker {

    private final static String QUEUE_NAME = "work";

    public static void main(String[] args) throws IOException, TimeoutException {


//        close();
        receive("work1");
        receive("work2");
        receive("work3");


    }

    private static void receive(final String flag) throws IOException, TimeoutException {
        // 创建连接
        Connection connection = RabbitMqFactory.newConnection();
        // 创建一个通道
        final Channel channel = connection.createChannel();
        // 指定一个队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        // 创建队列消费者
        final Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");

                System.out.println(flag +" Received '" + message + "'");
                try {
                    doWork(flag+":"+message);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally{
                    /*
                        手动消息应答机制,每完成一个,发送一次
                    */
                    channel.basicAck(envelope.getDeliveryTag(), false);
                }
            }

        };

        /*
                消息应答机制,true为开启,告诉mq消费者已经处理完,可以删除
         */
        boolean autoAck = false;
        channel.basicConsume(QUEUE_NAME, autoAck, consumer);

    }


    private static void close() throws IOException, TimeoutException {
        // 创建连接
        ConnectionFactory factory = new ConnectionFactory();
        // 设置 RabbitMQ 的主机名
        factory.setHost("172.16.1.177");
        // 创建一个连接
        Connection connection = factory.newConnection();
        // 创建一个通道
        final Channel channel = connection.createChannel();
        // 指定一个队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        //清除已经发送给对方的消费者
        channel.clearReturnListeners();
        //清除已经确认完成的消费者
        channel.clearConfirmListeners();

    }


    //模拟工作方法
    private static void doWork(String task) throws InterruptedException {
        String[] taskArr = task.split(":");
        TimeUnit.SECONDS.sleep(Long.valueOf(taskArr[2]));
        System.out.println(taskArr[0]+"做完第"+taskArr[2]+"个工作");
    }


    //

}
