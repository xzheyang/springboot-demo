package com.hy.springboot.demo.spark.service;

import java.util.ResourceBundle;

/**
 * @user yang.he
 * @date 2019/4/24
 * @introduce           spark配置项目
 **/
public class SparkConfigService {

    private static final String DRIVER_MEMORY;
    private static final String EXECUTOR_MEMORY;
    private static final String EXECUTOR_CORES;
    private static final String DEPLOY_MODE;
    private static final String MASTER;



    static {

        ResourceBundle bundle = ResourceBundle.getBundle("properties.spark");

        DRIVER_MEMORY = bundle.getString("spark.driver.memory");
        EXECUTOR_MEMORY = bundle.getString("spark.executor.memory");
        EXECUTOR_CORES = bundle.getString("spark.executor.cores");
        DEPLOY_MODE = bundle.getString("deployMode");
        MASTER = bundle.getString("master");


    }

    public static String getDriverMemory() {
        return DRIVER_MEMORY;
    }

    public static String getExecutorMemory() {
        return EXECUTOR_MEMORY;
    }

    public static String getExecutorCores() {
        return EXECUTOR_CORES;
    }

    public static String getDeployMode() {
        return DEPLOY_MODE;
    }

    public static String getMaster() {
        return MASTER;
    }

}
