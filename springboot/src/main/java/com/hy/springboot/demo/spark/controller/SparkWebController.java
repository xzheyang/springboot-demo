package com.hy.springboot.demo.spark.controller;

import com.hy.springboot.demo.common.bean.InvokeResult;
import com.hy.springboot.demo.common.bean.NewInvokeResult;
import com.hy.springboot.demo.spark.bean.DateInvoke;
import com.hy.springboot.demo.spark.service.BasicConfigService;
import com.hy.springboot.demo.spark.service.SparkLauncherController;
import org.springframework.web.bind.annotation.*;

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

    //spark服务提交控制
    private static volatile Boolean runFlag = false;

    @RequestMapping(value = "/sparkJob/run/{className}",method = RequestMethod.POST)
    public String run(@PathVariable("className") String className) throws IOException, InterruptedException {
        SparkLauncherController sparkLauncherController = new SparkLauncherController();

        boolean result = sparkLauncherController.submit(BasicConfigService.getAddress(), className,new String[]{});
        if (result)
            return "success: "+LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:hh:ss"));
        return "false: "+LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:hh:ss"));
    }


    /**
     *  同步大数据接口
     *
     * @param request       isFirst(是否是同步所有数据,注意调用后,大数据端所有数据清空)
     *                      businessDate(导入日期)
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    @RequestMapping(value = "/sparkJob/migrate",method = RequestMethod.POST)
    public String migrate(HttpServletRequest request) throws IOException, InterruptedException {

        String isFirst = request.getParameter("isFirst");
        String businessDate = request.getParameter("businessDate");

        SparkLauncherController sparkLauncherController = new SparkLauncherController();
        Boolean result = sparkLauncherController.submit(BasicConfigService.getAddress(),BasicConfigService.getMigrateClasspath(),new String[]{isFirst,businessDate});

        return result.toString();
    }

    /**
     *  特殊同步大数据接口(之前导入失败后)
     *
     * @param request
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
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


    /**
     *  风险评级的调用接口
     *
     * @param request      isFirst是否首次上线    businessDate(跑批日期)
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    @RequestMapping(value = "/sparkJob/rating",method = RequestMethod.POST)
    public String rating(HttpServletRequest request) throws IOException, InterruptedException {

        String isFirst =request.getParameter("isFirst");
        String businessDate=request.getParameter("businessDate");


        SparkLauncherController sparkLauncherController = new SparkLauncherController();
        Boolean result = sparkLauncherController.submit(BasicConfigService.getAddress(),BasicConfigService.getRatingClasspath(),new String[]{isFirst,businessDate});

        return result.toString();
    }



    /**
     *  规则筛选调用接口
     *
     * @param request       businessDate(跑批日期)
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    @RequestMapping(value = "/sparkJob/ruleProcess",method = RequestMethod.POST)
    public String rule(HttpServletRequest request) throws IOException, InterruptedException {

        String businessDate = request.getParameter("businessDate");

        SparkLauncherController sparkLauncherController = new SparkLauncherController();
        Boolean result = sparkLauncherController.submit(BasicConfigService.getAddress(),BasicConfigService.getRuleClasspath(),new String[]{businessDate});

        return result.toString();
    }


    /**
     *  批量规则筛选调用接口
     *
     * @param request       businessDate(跑批日期)
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    @RequestMapping(value = "/sparkJob/ruleManyProcess",method = RequestMethod.POST)
    public String ruleMany(HttpServletRequest request) throws IOException, InterruptedException {

        String businessDates = request.getParameter("businessDates");

        SparkLauncherController sparkLauncherController = new SparkLauncherController();
        Boolean result = sparkLauncherController.submit(BasicConfigService.getAddress(),BasicConfigService.getRuleManyClasspath(),new String[]{businessDates});

        return result.toString();
    }



    /**
     *  负面清单调用接口
     *
     * @param request      isFirst是否首次上线    businessDate(跑批日期)
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    @RequestMapping(value = "/sparkJob/identify",method = RequestMethod.POST)
    public String identify(HttpServletRequest request) throws IOException, InterruptedException {

        String isFirst =request.getParameter("isFirst");
        String businessDate=request.getParameter("businessDate");

        SparkLauncherController sparkLauncherController = new SparkLauncherController();
        Boolean result = sparkLauncherController.submit(BasicConfigService.getAddress(),BasicConfigService.getIdentifyClasspath(),new String[]{isFirst,businessDate});

        return result.toString();
    }


    /**
     *  指定某特定自然人名单筛查
     *
     * @param request      uid  负面清单模板id，如果有多个id时用“|”分隔
     *
     * @return      isSuccess (接口是否执行成功 1:成功 0:失败)
     *              returnMsg (当isSuccess=1时，返回接口执行成功,当isSuccess=0时，返回错误信息)
     *
     */
    @RequestMapping(value = "/sparkJob/identifyByUids",method = RequestMethod.POST)
    public InvokeResult identifyByUids(HttpServletRequest request) throws IOException, InterruptedException {

        String uid = request.getParameter("uid");

        SparkLauncherController sparkLauncherController = new SparkLauncherController();
        Boolean result = sparkLauncherController.submit(BasicConfigService.getAddress(),BasicConfigService.getIdentifyBycustClasspath(),new String[]{uid});

        if (result){
            return new InvokeResult("1","success");
        }

        return new InvokeResult("0","error");
    }


    /**
     *  黑名单对外调用接口
     *
     * @param request       businessDate(跑批日期)
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    @RequestMapping(value = "/sparkJob/blacklist",method = RequestMethod.POST)
    public String blacklist(HttpServletRequest request) throws IOException, InterruptedException {

        String businessDate = request.getParameter("businessDate");

        SparkLauncherController sparkLauncherController = new SparkLauncherController();
        Boolean result = sparkLauncherController.submit(BasicConfigService.getAddress(),BasicConfigService.getBlacklistClasspath(),new String[]{businessDate});

        return result.toString();
    }

    /**
     *  黑名单对外调用接口
     *
     * @param request       modelNo 黑名单模板编号，如果有多个编号时用“|”分隔
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    @RequestMapping(value = "/sparkJob/blacklistByModelNo",method = RequestMethod.POST)
    public InvokeResult blacklistByModelNo(HttpServletRequest request) throws IOException, InterruptedException {

        String modelNo = request.getParameter("modelNo");

        SparkLauncherController sparkLauncherController = new SparkLauncherController();
        Boolean result = sparkLauncherController.submit(BasicConfigService.getAddress(),BasicConfigService.getBlacklistBymodelClasspath(),new String[]{modelNo});

        if (result){
            return new InvokeResult("1","success");
        }

        return new InvokeResult("0","error");
    }


    /**
     *  数据监控调用接口
     *
     * @param dateInvoke      时间调用
     *
     * @return
     */
    @RequestMapping(value = "/sparkJob/dataMonitor",method = RequestMethod.POST)
    public NewInvokeResult save(@RequestBody DateInvoke dateInvoke) throws IOException, InterruptedException {

        SparkLauncherController sparkLauncherController = new SparkLauncherController();
        Boolean result = sparkLauncherController.submit(BasicConfigService.getAddress(),BasicConfigService.getDataMonitorClasspath(),new String[]{dateInvoke.getBusinessDate()});

        if (result){
            return new NewInvokeResult("1","success");
        }

        return new NewInvokeResult("0","error");

    }


}
