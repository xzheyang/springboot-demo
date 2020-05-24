package com.hy.springboot.use.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @user yang.he
 * @date 2019/9/20
 * @introduce
 **/

@RunWith(SpringRunner.class)
@SpringBootTest
public class BasicServiceTest {

    @Autowired
    private BasicService basicService;


    @Test
    public void test(){
        basicService.hello();
    }


}