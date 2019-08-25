package com.hy.springboot.thread.deferedresult_multithreading.dao;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * Queue监听器
 * 处理订单数据
 *
 */
@Component
public class QueueListener implements ApplicationListener<ContextRefreshedEvent> {

    private static final Logger logger = LoggerFactory.getLogger(QueueListener.class);

    @Autowired
    private MockQueue mockQueue;
    @Autowired
    private DeferredResultHolder deferredResultHolder;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        new Thread(() -> {
            while (true) {
                //判断CompleteOrder字段是否是空
                if (StringUtils.isNotBlank(mockQueue.getCompleteOrder())) {
                    String orderNumber = mockQueue.getCompleteOrder();

                    deferredResultHolder.getMap().get(orderNumber).setResult("place order success");
                    logger.info("返回订单处理结果");
                    //将CompleteOrder设为空，表示处理成功
                    mockQueue.setCompleteOrder(null);
                } else {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}

