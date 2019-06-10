package com.hy.springboot.sample.aop.module;

import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @user yang.he
 * @date 2019/6/10
 * @introduce
 **/

@SpringBootApplication
public class AopApp {

    public void say() {
        System.out.println("AOP TEST");
    }

    public static void main(String[] args) {
        AopApp app = new AopApp();
        app.say();
    }

}
