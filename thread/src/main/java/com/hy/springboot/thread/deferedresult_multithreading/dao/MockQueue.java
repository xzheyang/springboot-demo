package com.hy.springboot.thread.deferedresult_multithreading.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 *
 * 模拟消息队列类
 * 注意这里没有实现线程安全
 * 其实就是没有实现队列
 *
 * */
@Component
public class MockQueue {

    private static final Logger LOGGER = LoggerFactory.getLogger(MockQueue.class);

    //下单消息
    private String placeOrder;

    //订单完成消息
    private String completeOrder;


    public String getPlaceOrder() {        return placeOrder;    }


    @Async
    public void setPlaceOrder(String placeOrder) throws InterruptedException {

        LOGGER.info("接到下单请求" + placeOrder);
        //模拟处理
        try {
            Thread.sleep(1000);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //给completeOrder赋值
        this.completeOrder = placeOrder;
        LOGGER.info("下单请求处理完毕" + placeOrder);

    }


    public void setPlaceOrder2(String placeOrder) throws InterruptedException {
        new Thread(() -> {
            LOGGER.info("接到下单请求" + placeOrder);
            //模拟处理
            try {
                Thread.sleep(1000);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //给completeOrder赋值
            this.completeOrder = placeOrder;
            LOGGER.info("下单请求处理完毕" + placeOrder);
        }).start();
    }

    public String getCompleteOrder() {
        return completeOrder;
    }

    public void setCompleteOrder(String completeOrder) {
        this.completeOrder = completeOrder;
    }
}

