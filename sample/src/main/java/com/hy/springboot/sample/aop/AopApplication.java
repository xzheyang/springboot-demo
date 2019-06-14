package com.hy.springboot.sample.aop;

import com.hy.springboot.sample.aop.module.AopApp;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @user yang.he
 * @date 2019/6/12
 * @introduce
 **/

@SpringBootApplication
public class AopApplication {

    public static void main(String[] args) {
        AopApp app = new AopApp();
        app.test();
    }

}
