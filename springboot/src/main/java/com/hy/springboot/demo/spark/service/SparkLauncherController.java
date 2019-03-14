package com.hy.springboot.demo.spark.service;

import org.apache.spark.launcher.SparkAppHandle;
import org.apache.spark.launcher.SparkLauncher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.CountDownLatch;

/**
 * @USER yang.he
 * @date old
 * @introduce            各大项目调用dataCalculate平台的的调用控制接口,需要把jar包和配置文件放入项目的指定的文件夹下
 *
 **/
public class SparkLauncherController {


    //todo 还差个灵活版本


    private static final Logger logger = LoggerFactory.getLogger(SparkLauncherController.class);


    /**
     *    SparkLauncher提交接口,这里会有参数控制调用那个类的mian方法
     *
     * @param jarAddress                jar包地址
     * @param mainControllerClass       运行main方法的指定包名+类名
     * @return
     * @throws InterruptedException
     * @throws IOException
     */
    public boolean submit(String jarAddress,String mainControllerClass) throws InterruptedException, IOException {


        /**
         *      判断是否继承了运行的抽象接口
         */


        /**
         *
         * 属性设置
         *
         *
         */
        HashMap<String,String> env = new HashMap<>(30);
        //运行jar的位置和类的main方法
        env.put("jar",jarAddress);
        env.put("class",mainControllerClass);
        //内存和cpu配置
        env.put("spark.driver.memory","3g");
        env.put("spark.executor.memory", "3g");
        env.put("spark.executor.cores","4");
        //地址配置
        env.put("HADOOP_CONF_DIR", "/etc/hadoop");
        env.put("JAVA_HOME", "/opt/jdk1.8.0_181");
        env.put("sparkHome","/opt/cloudera/parcels/SPARK2/lib/spark2");
        //运行配置
        env.put("deployMode","cluster");
        env.put("master","yarn");
        env.put("YARN_CONF_DIR","/etc/hadoop/conf.cloudera.yarn");

        env.put("spark.app.id","10086");

        //并行数量
//        env.put("spark.default.parallelism","10");
        //是否运行多个context在一个运行中运行,有些人是true
        env.put("spark.driver.allowMultipleContexts","false");
        env.put("spark.akka.frameSize","200");


        //程序结束计数器
        CountDownLatch countDownLatch = new CountDownLatch(1);


        //这里调用setJavaHome()方法后，JAVA_HOME is not set 错误依然存在
        SparkAppHandle handle = new SparkLauncher(env)
                .setSparkHome(env.get("sparkHome"))
                .setAppResource(env.get("jar"))
                .setMainClass(env.get("class"))
                .setMaster(env.get("master"))
                .setDeployMode(env.get("deployMode"))
                .setConf("spark.app.id", env.get("spark.app.id"))
                .setConf("spark.driver.memory", env.get("spark.driver.memory"))
                .setConf("spark.akka.frameSize", env.get("spark.akka.frameSize"))
                .setConf("spark.executor.memory", env.get("spark.executor.memory"))
                .setConf("spark.executor.instances", "5")
                .setConf("spark.executor.cores", env.get("spark.executor.cores"))
//                .setConf("spark.default.parallelism",env.get("spark.default.parallelism"))
                .setConf("spark.driver.allowMultipleContexts", env.get("spark.driver.allowMultipleContexts"))
                .addSparkArg("--files", "/etc/hive/conf/hive-site.xml")        //添加hive依赖关系
                .setVerbose(true).startApplication(new SparkAppHandle.Listener() {
                    //这里监听任务状态，当任务结束时（不管是什么原因结束）,isFinal（）方法会返回true,否则返回false
                    @Override
                    public void stateChanged(SparkAppHandle sparkAppHandle) {
                        if (sparkAppHandle.getState().isFinal()) {
                            countDownLatch.countDown();
                        }
                        logger.info("state:" + sparkAppHandle.getState().toString());
                    }


                    @Override
                    public void infoChanged(SparkAppHandle sparkAppHandle) {
                        logger.info("Info:" + sparkAppHandle.getState().toString());
                    }
                });


        /**
         *  程序结束控制
         *  todo
         *  监测job是否执行成功,功能重试在api中
         *  错误重试或报告
         *
         */
        //fixme 换为log
        logger.info("The task is executing, please wait ....");
        //线程等待任务结束
        countDownLatch.await();
        logger.info("The task is finished!");


        //判断数据是否完成
        return (SparkAppHandle.State.FINISHED == handle.getState());

    }



    //调用jar的命令
    //java -classpath hadoop-train.jar com.sinitek.dc.spark.sparktemplate.controller.sparklauncher.SparkLauncherController [args]
    //单纯打包找不到类,需发布web程序访问


}
