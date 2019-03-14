package com.hy.springboot.demo.spark.controller;

import com.hy.springboot.demo.spark.service.SparkLauncherController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @user yang.he
 * @date 2019/3/5
 * @introduce       SparkLauncher测试
 **/

@RestController
public class SparkWebController {

    @RequestMapping(value = "/sparkJob/run/{className}",method = RequestMethod.POST)
    public String run(@PathVariable("className") String className) throws IOException, InterruptedException {
        SparkLauncherController sparkLauncherController = new SparkLauncherController();
        boolean result = sparkLauncherController.submit("hdfs://hslave2:8020/user/feng/jar_b/fxq_st-1.0-SNAPSHOT.jar", className);
        if (result)
            return "success: "+LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:hh:ss"));
        return "false: "+LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:hh:ss"));
    }

}
