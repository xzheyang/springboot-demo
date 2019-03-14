package com.hy.springboot.sample.test;


import com.hy.springboot.sample.async.IAsync;
import com.hy.springboot.sample.async.entities.AsyncTask;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.concurrent.*;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = IAsync.class)
public class IAsyncTest {

    @Autowired
    AsyncTask task;

    private static final Logger logger = LoggerFactory.getLogger(IAsyncTest.class);

    @Test
    public void  test() throws InterruptedException, ExecutionException {

        /**
         *    1.static加上@Async也不是并发
         *    2.必须是spring注入的类,而不是自己new
         *
         */
        Future<String> task1 = task.doTask1();
        Future<String> task2 = task.doTask2();
        Future<String> task3 = task.doTask3();


        while(true) {


            try {
                String task3Result = task3.get(3,TimeUnit.SECONDS);
                //最大等待3秒获取task3结果

            } catch (TimeoutException e) {
                boolean taskOver = task3.cancel(true);
                //参数表示是否允许取消正在执行却没有执行完毕的任务
                //返回值不管参数是什么,只要任务执行完成就返回true,没执行完成或没执行就返回false;
                boolean taskCancelled = task3.isCancelled();
                //表示任务是否被取消成功,如果在任务正常完成前被取消成功,则返回 true。
                if (taskOver&&taskCancelled){
                    logger.warn("任务3被取消");
                }else {
                    logger.warn("任务3虽然被执行取消,但是正常完成");
                }
            } catch (CancellationException e){

            }


            //经过测验jdbc要使用线程池才能正确使用@Async

            if (task1.isDone() && task2.isDone() && task3.isDone()) {
                // 前两个任务都调用完成，退出循环等待
                break;
            }

        }


    }


}
