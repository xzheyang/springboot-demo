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
    private static final String SPECIAL_MIGRATE_CLASSPATH;
    private static final String TABLE_MIGRATE_CLASSPATH;
    private static final String IDENTIFY_CLASSPATH;
    private static final String BLACKLIST_CLASSPATH;
    private static final String IDENTIFY_BYCUST_CLASSPATH;
    private static final String BLACKLIST_BYMODEL_CLASSPATH;
    private static final String RULE_MANY_CLASSPATH;
    private static final String DATA_MONITOR_CLASSPATH;

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
        SPECIAL_MIGRATE_CLASSPATH = bundle.getString("special_migrate_classpath");
        TABLE_MIGRATE_CLASSPATH = bundle.getString("table_migrate_classpath");
        IDENTIFY_CLASSPATH  = bundle.getString("identify_classpath");
        BLACKLIST_CLASSPATH = bundle.getString("blacklist_classpath");

        IDENTIFY_BYCUST_CLASSPATH = bundle.getString("identify_bycust_classpath");
        BLACKLIST_BYMODEL_CLASSPATH = bundle.getString("blacklist_bymodel_classpath");

        RULE_MANY_CLASSPATH = bundle.getString("rule_many_classpath");

        DATA_MONITOR_CLASSPATH = bundle.getString("data_monitor_classpath");
    }

    public static String getIdentifyBycustClasspath() {
        return IDENTIFY_BYCUST_CLASSPATH;
    }

    public static String getBlacklistBymodelClasspath() {
        return BLACKLIST_BYMODEL_CLASSPATH;
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

    public static String getSpecialMigrateClasspath() {
        return SPECIAL_MIGRATE_CLASSPATH;
    }

    public static String getTableMigrateClasspath() {
        return TABLE_MIGRATE_CLASSPATH;
    }

    public static String getIdentifyClasspath() {
        return IDENTIFY_CLASSPATH;
    }

    public static String getBlacklistClasspath() {
        return BLACKLIST_CLASSPATH;
    }

    public static String getRuleManyClasspath() {
        return RULE_MANY_CLASSPATH;
    }

    public static String getDataMonitorClasspath() {
        return DATA_MONITOR_CLASSPATH;
    }

}
