package com.hy.springboot.sample.aop;

import com.hy.springboot.sample.aop.invoke.NativeAnnoAspect;
import com.hy.springboot.sample.aop.module.AopApp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @user yang.he
 * @date 2019/6/12
 * @introduce
 **/

@SpringBootApplication
public class AopApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(NativeAnnoAspect.class);

    public static void main(String[] args) {
//        LOGGER.error("test");
        AopApp app = new AopApp();
        app.test();
    }

}
