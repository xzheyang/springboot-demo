package com.hy.springboot.sample.async;


import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;


@SpringBootApplication
@EnableAsync
@ComponentScan
public class IAsync {


    /**
     *    1.static加上@Async也是并发
     *
     */
    public static void main(String[] args) {
        SpringApplication.run(IAsync.class, args);
    }



    /*
            自定义线程池,供异步调用使用
            而且我用jdbc时,似乎必须使用线程池.否则跳过(未明白)
     */
    @Configuration
    class TaskPoolConfig {

        @Bean("taskExecutor")
        public Executor taskExecutor() {
            ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
            executor.setCorePoolSize(10);
            executor.setMaxPoolSize(20);
            executor.setQueueCapacity(200);
            executor.setKeepAliveSeconds(60);
            //等待线程完成后再关闭连接(为redis准备)
            executor.setWaitForTasksToCompleteOnShutdown(true);
            //最长等待断开时间(为redis准备)
            executor.setAwaitTerminationSeconds(60);
            executor.setThreadNamePrefix("taskExecutor-");
            executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
            return executor;
        }

    }


}
