package com.hy.springboot.sample.aop;

import com.hy.springboot.sample.aop.module.AopApp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


/**
 * @user yang.he
 * @date 2019/6/12
 * @introduce
 **/

@SpringBootApplication
@ComponentScan
public class AopApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(AopApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(AopApplication.class, args);
        LOGGER.warn("test");
        AopApp app = new AopApp();
        app.test();
    }

}
