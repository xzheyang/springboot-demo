package com.hy.springboot.demo.timing;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @user yang.he
 * @date 2019/3/4
 * @introduce       JDK定时任务介绍
 **/
public class JdkTimer {

    public static void delay(int second) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("延迟"+second+"秒,执行这里的接口");
                System.exit(0); //需要有退出接口
            }
        },second*1000);
    }

    public static void timing(Date date) {
        Timer timer = new Timer();
        timer.schedule(new Task(),date);
    }

    public static void cycle(Date date,long period) {
        Timer timer = new Timer();
        timer.schedule(new Task(),date,period*1000);
    }


    static class Task extends TimerTask{
        @Override
        public void run() {
            System.out.println("定时任务");
        }
    }


    //test
    public static void main(String[] args) {

        ZoneId zone = ZoneId.systemDefault();
        Date date = Date.from(LocalDateTime.now().plusSeconds(3).atZone(zone).toInstant());

//        delay(3);
        timing(date);
        cycle(date,3);

    }


}
