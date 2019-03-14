package com.hy.springboot.demo.timing;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @user yang.he
 * @date 2019/3/4
 * @introduce       springBoot的定时任务
 **/

@Component
public class SpringBootScheduler {

    /*
            需要在Application中@EnableScheduling允许
     */

    @Scheduled(fixedRate = 5000)
    public void reportCurrentTime() {
        System.out.println("现在时间：" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    }

    /*
        introduce

        @Scheduled(fixedRate = 5000) ：上一次开始执行时间点之后5秒再执行
        @Scheduled(fixedDelay = 5000) ：上一次执行完毕时间点之后5秒再执行
        @Scheduled(initialDelay=1000, fixedRate=5000) ：第一次延迟1秒后执行，之后按fixedRate的规则每5秒执行一次
        @Scheduled(cron="* /5 * * * * *") ：通过cron表达式定义规则

     */

}
