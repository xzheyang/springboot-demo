package com.hy.springboot.sample.aop.invoke;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

/**
 * @user yang.he
 * @date 2019/6/10
 * @introduce
 **/

@Aspect
public class NativeAnnoAspect {

    //fixme 原生AOP需要加载maven插件

    //call在调用的方法的地方通知,而execution是在执行方法的环境
    @Pointcut("execution(* com.hy.springboot.sample.aop.module.AopApp.say(..))")
    public void jointPoint() {
    }

    @Before("jointPoint()")
    public void before() {
        System.out.println("NativeAnnoAspect before say");
    }

    @After("jointPoint()")
    public void after() {
        System.out.println("NativeAnnoAspect after say");
    }
}
