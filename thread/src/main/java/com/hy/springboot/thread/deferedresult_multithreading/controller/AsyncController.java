package com.hy.springboot.thread.deferedresult_multithreading.controller;


import com.hy.springboot.thread.deferedresult_multithreading.dao.DeferredResultHolder;
import com.hy.springboot.thread.deferedresult_multithreading.dao.MockQueue;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.concurrent.Callable;

@RestController
public class AsyncController {

    private static final Logger logger = LoggerFactory.getLogger(AsyncController.class);

    //注入模拟消息队列类
    @Autowired
    private MockQueue mockQueue;
    @Autowired
    private DeferredResultHolder deferredResultHolder;

    @RequestMapping("/orderMockQueue")
    public DeferredResult orderQueue() throws InterruptedException {
        logger.info("主线程开始");
        //随机生成8位数
        String orderNumber = RandomStringUtils.randomNumeric(8);

        //没有考虑多个用户,订单被覆盖的情况
        mockQueue.setPlaceOrder(orderNumber);

        //
        DeferredResult result = new DeferredResult();
        deferredResultHolder.getMap().put(orderNumber, result);
        //等待1秒,让监听器处理订单
        Thread.sleep(1000);
        logger.info("主线程返回");

        /*
                mvc的异步必须返回这个
         */
        return result;
    }


    /**
     * 用Callable实现异步
     *
     */
    @RequestMapping("/orderAsync")
    public Callable orderAsync() throws InterruptedException {
        logger.info("主线程开始");
        Callable result = new Callable() {
            @Override
            public Object call() throws Exception {
                logger.info("副线程开始");
                Thread.sleep(1000);
                logger.info("副线程返回");
                return "success";
            }
        };
        logger.info("主线程返回");
        return result;
    }


    /**
     * 单线程测试
     */
    @RequestMapping("/order")
    public String order() throws InterruptedException {
        logger.info("主线程开始");
        Thread.sleep(1000);
        logger.info("主线程返回");
        return "success";
    }




}