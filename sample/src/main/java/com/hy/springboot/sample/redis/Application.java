package com.hy.springboot.sample.redis;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;


@SpringBootApplication
@EnableAsync
@ComponentScan
public class Application {



    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }



}
