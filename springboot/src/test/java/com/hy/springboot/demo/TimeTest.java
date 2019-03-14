package com.hy.springboot.demo;

import com.hy.springboot.demo.timing.SpringBootScheduler;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * @user yang.he
 * @date 2019/3/4
 * @introduce
 **/

@RunWith(SpringRunner.class)
@SpringBootTest
public class TimeTest {


    @Test
    public void test() {

        SpringBootScheduler scheduler= new SpringBootScheduler();
        scheduler.reportCurrentTime();


    }

}
