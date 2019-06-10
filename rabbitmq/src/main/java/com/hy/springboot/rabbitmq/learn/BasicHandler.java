package com.hy.springboot.rabbitmq.learn;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeoutException;

/**
 * @user yang.he
 * @date 2019/3/16
 * @introduce           根据官方文档的 RabbitMQ基础操作
 **/
public class BasicHandler {


    /**
     *
     * @param exchangeName         交换机名称
     * @param exchangeType         交换机类型
     * @param routingKey
     *
     */
    private void create(String exchangeName ,String exchangeType, String routingKey) throws IOException, TimeoutException {

        //创立连接工厂类,配置参数
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("172.16.1.177");
        factory.setUsername("admin");
        factory.setPassword("admin");
//        factory.setPort("");
//        factory.setUri("amqp:// userName:password @ hostName:portNumber / virtualHost");      //可使用uri连接

        //创立连接
        Connection conn = factory.newConnection();
        final Channel channel = conn.createChannel();

        //交换机类型区别
        //声明交换机(第三个参数是持久化,重启mq依旧存在,详情见源代码注释)
        channel.exchangeDeclare(exchangeName,exchangeType,false);

        //声明一个临时的队列
        String queueName = channel.queueDeclare().getQueue();

        //绑定交换机与队列
        channel.queueBind(queueName,exchangeName,routingKey);


        //定义消费者
        Consumer consumer = new DefaultConsumer(channel){

            /**
             * @param consumerTag
             * @param envelope
             * @param properties    属性
             * @param body          接收的消息
             */
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println(" Received '" + message + "'");
                // 手动应答
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        };

        //进行消费,并且设置手动应答
        channel.basicConsume(queueName, false, consumer);



        //关闭通道,其实不是必须的,当底层连接关闭时是自动完成的
//        channel.close();
//        conn.close();

    }

    private void submit(Channel channel,String exchangeName) throws IOException {

        String message = "测试";
        channel.basicPublish(exchangeName, "", null, message.getBytes());


    }


}
