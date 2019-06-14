package com.hy.springboot.demo.spark.controller;

import com.hy.springboot.demo.spark.service.BasicConfigService;
import com.hy.springboot.demo.spark.service.SparkLauncherController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
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

        boolean result = sparkLauncherController.submit(BasicConfigService.getAddress(), className,new String[]{});
        if (result)
            return "success: "+LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:hh:ss"));
        return "false: "+LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:hh:ss"));
    }


    @RequestMapping(value = "/sparkJob/migrate",method = RequestMethod.POST)
    public String migrate(HttpServletRequest request) throws IOException, InterruptedException {

        String isFirst = request.getParameter("isFirst");
        String businessDate = request.getParameter("businessDate");

        SparkLauncherController sparkLauncherController = new SparkLauncherController();
        Boolean result = sparkLauncherController.submit(BasicConfigService.getAddress(),BasicConfigService.getMigrateClasspath(),new String[]{isFirst,businessDate});

        return result.toString();
    }

    @RequestMapping(value = "/sparkJob/specialMigrate",method = RequestMethod.POST)
    public String specialMigrate(HttpServletRequest request) throws IOException, InterruptedException {

        String businessStartDate = request.getParameter("businessStartDate");
        String businessEndDate = request.getParameter("businessEndDate");

        SparkLauncherController sparkLauncherController = new SparkLauncherController();
        Boolean result = sparkLauncherController.submit(BasicConfigService.getAddress(),BasicConfigService.getSpecialMigrateClasspath(),new String[]{businessStartDate,businessEndDate});

        return result.toString();

    }

    @RequestMapping(value = "/sparkJob/specialTableMigrate",method = RequestMethod.POST)
    public String specialTableMigrate(HttpServletRequest request) throws IOException, InterruptedException {

        String rdbmsTableName = request.getParameter("rdbmsTableName");
        String database = request.getParameter("database");
        String hiveTableName = request.getParameter("hiveTableName");
        String splitColName = request.getParameter("splitColName");

        SparkLauncherController sparkLauncherController = new SparkLauncherController();
        Boolean result = sparkLauncherController.submit(BasicConfigService.getAddress(),BasicConfigService.getTableMigrateClasspath(),new String[]{rdbmsTableName,database,hiveTableName,splitColName});

        return result.toString();
    }


    @RequestMapping(value = "/sparkJob/rating",method = RequestMethod.POST)
    public String rating(HttpServletRequest request) throws IOException, InterruptedException {

        String ruleType=request.getParameter("ruleType");
        String businessDate=request.getParameter("businessDate");


        SparkLauncherController sparkLauncherController = new SparkLauncherController();
        Boolean result = sparkLauncherController.submit(BasicConfigService.getAddress(),BasicConfigService.getRatingClasspath(),new String[]{ruleType,businessDate});

        return result.toString();
    }


    @RequestMapping(value = "/sparkJob/ruleProcess",method = RequestMethod.POST)
    public String rule(HttpServletRequest request) throws IOException, InterruptedException {

        String businessDate = request.getParameter("businessDate");

        SparkLauncherController sparkLauncherController = new SparkLauncherController();
        Boolean result = sparkLauncherController.submit(BasicConfigService.getAddress(),BasicConfigService.getRuleClasspath(),new String[]{businessDate});

        return result.toString();
    }


}
