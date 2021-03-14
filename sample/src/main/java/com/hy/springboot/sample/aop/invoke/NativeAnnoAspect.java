package com.hy.springboot.sample.aop.invoke;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @user yang.he
 * @date 2019/6/10
 * @introduce
 **/

@Aspect
public class NativeAnnoAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(NativeAnnoAspect.class);

    /*
    fixme 注意点:
            1.原生AOP需要加载maven插件
            2.上级目录调用不会织入
    */

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

    //环绕增强好像因为优先级和其他的增强有冲突,要注释
//    @Around("jointPoint()")
//    public Object around(ProceedingJoinPoint joinPoint) {
//        System.out.println("NativeAnnoAspect around say");
//
//        Object result = printLog(joinPoint, LogLevel.TRACE, "正常日志");
//
//
//        //通过
//        Object proceed = null;
//        try {
//            proceed = joinPoint.proceed();
//        } catch (Throwable throwable) {
//            throwable.printStackTrace();
//        }
//
//        return proceed;
//    }


    private Object printLog(JoinPoint joinPoint,LogLevel level, String describe) {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        
        try {
            if (joinPoint instanceof ProceedingJoinPoint) {
                return ((ProceedingJoinPoint) joinPoint).proceed(joinPoint.getArgs());
            }
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            logGradePrint(level,getClass().getSimpleName(), describe + " : " + signature.toLongString());
        }
        return null;
    }

    private void logGradePrint(LogLevel level,String className,String logInfo){

        switch (level){
            case TRACE: LOGGER.trace(className,logInfo); break;
            case INFO:  LOGGER.info(className,logInfo); break;
            case WARN:  LOGGER.warn(className,logInfo); break;
            case ERROR: LOGGER.error(className,logInfo); break;
        }

    }

    public enum LogLevel {

        TRACE,
        INFO,
        WARN,
        ERROR;

    }


}
