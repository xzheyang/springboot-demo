package com.hy.springboot.sample.redis;


import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;


@SpringBootApplication
@EnableAsync
@ComponentScan
public class Applaction {



    public static void main(String[] args) {
        SpringApplication.run(Applaction.class, args);
    }



}
