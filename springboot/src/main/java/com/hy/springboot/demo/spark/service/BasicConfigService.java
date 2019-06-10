package com.hy.springboot.demo.spark.service;

import java.util.ResourceBundle;

/**
 * @user yang.he
 * @date 2019/4/23
 * @introduce
 **/
public class BasicConfigService {

    private static final String HOST_IP;
    private static final String JAR_ADDRESS;
    private static final String RULE_CLASSPATH;
    private static final String RATING_CLASSPATH;
    private static final String JAVA_HOME;
    private static final String SPARK_HOME;
    private static final String HADOOP_CONF_DIR;
    private static final String YARN_CONF_DIR;
    private static final String MIGRATE_CLASSPATH;

    static {

        ResourceBundle bundle = ResourceBundle.getBundle("properties.basic");

        HOST_IP = bundle.getString("host_ip");
        JAR_ADDRESS = bundle.getString("jar_address");
        RULE_CLASSPATH = bundle.getString("rule_classpath");
        RATING_CLASSPATH = bundle.getString("rating_classpath");
        JAVA_HOME = bundle.getString("java_home");
        SPARK_HOME = bundle.getString("spark_home");
        HADOOP_CONF_DIR = bundle.getString("hadoop_conf_dir");
        YARN_CONF_DIR = bundle.getString("yarn_conf_dir");
        MIGRATE_CLASSPATH = bundle.getString("migrate_classpath");

    }

    public static String getHostIp() {
        return HOST_IP;
    }

    public static String getJarAddress() {
        return JAR_ADDRESS;
    }

    public static String getAddress() {
        return HOST_IP+JAR_ADDRESS;
    }

    public static String getRuleClasspath() {
        return RULE_CLASSPATH;
    }

    public static String getRatingClasspath() {
        return RATING_CLASSPATH;
    }

    public static String getJavaHome(){ return  JAVA_HOME; }

    public static String getSparkHome(){ return SPARK_HOME; }

    public static String getHadoopConfDir(){ return HADOOP_CONF_DIR; }

    public static String getYarnConfDir(){ return YARN_CONF_DIR; }

    public static String getMigrateClasspath() {
        return MIGRATE_CLASSPATH;
    }
}