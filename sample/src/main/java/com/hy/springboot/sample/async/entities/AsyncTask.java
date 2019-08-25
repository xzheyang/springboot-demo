package com.hy.springboot.sample.async.entities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.concurrent.Future;

/**
 *  测试并发的task
 *
 **/
@Component
public class AsyncTask {

    public static Random random = new Random();

    private static final Logger logger = LoggerFactory.getLogger(AsyncTask.class);

    @Async
    public Future<String> doTask1() throws InterruptedException {
        logger.info("任务1开始");
        long start = System.currentTimeMillis();
        Thread.sleep(random.nextInt(3000));    //时间随机
        long end = System.currentTimeMillis();
        logger.info("完成任务1,耗时: "+(end-start));

        return new AsyncResult<String>("任务1完成");
    }

    @Async
    public Future<String> doTask2() throws InterruptedException {
        logger.info("任务2开始");
        long start = System.currentTimeMillis();
        Thread.sleep(random.nextInt(3000));
        long end = System.currentTimeMillis();
        logger.info("完成任务2,耗时: "+(end-start));

        return new AsyncResult<String>("任务2完成");
    }

    @Async
    public Future<String> doTask3() throws InterruptedException {
        logger.info("任务3开始");
        long start = System.currentTimeMillis();
        Thread.sleep(random.nextInt(10000));
        long end = System.currentTimeMillis();
        logger.info("完成任务3,耗时: "+(end-start));

        return new AsyncResult<String>("任务3完成");
    }

}
