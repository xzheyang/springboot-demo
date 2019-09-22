package com.hy.springboot.use.test;

import org.springframework.stereotype.Component;

/**
 * @user yang.he
 * @date 2019/9/20
 * @introduce
 **/

@Component
public class BasicService {

    public void hello() {
        System.out.println("基础service测试");
    }

}
