package com.hy.springboot.use.properties;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @user yang.he
 * @date 2019/9/17
 * @introduce
 **/

@RunWith(SpringRunner.class)
@SpringBootTest
public class PropertiesSuitTest {



    @Autowired
    private PropertiesSuit propertiesSuit;

    @Test
    public void hello(){
        List<String> urls = propertiesSuit.getUrls();
        System.out.println("urls: "+urls);
        String title = propertiesSuit.getTitle1();
        System.out.println("title: "+title);
    }


}