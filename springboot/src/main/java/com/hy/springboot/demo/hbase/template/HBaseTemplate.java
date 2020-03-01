package com.hy.springboot.demo.hbase.template;

import org.apache.commons.lang3.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.io.Serializable;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @USER PC
 * @DATE ${time}
 * @文件说明
 **/
public class HBaseTemplate implements Serializable {

    //连接
    private static Connection connection;
    //管理权限
    private static Admin admin;
    //配置
    private static Configuration configuration;

    //todo 固定只有一个HBase调用它           是否有完善的context,创建一个学到了什么
    private static HBaseTemplate instance;

    //判断为空才是解决spark外必须序列化的优点实现
    private static Map<String,HTable> hTableMap = new ConcurrentHashMap<>();

    //解决序列化问题
    private static ThreadLocal<HTable> hTableThreadLocal = new ThreadLocal<>();

    //初始化
    private static void init(Configuration conf) {
        createConnection(conf);
    }


    //创建默认参数的连接配置
    public static Configuration createConfiguration() {
        //获得连接设置
        Configuration conf = HBaseConfiguration.create();
        //设置zooKeeper集群地址，也可以通过将hbase-site.xml导入classpath，但是建议在程序里这样设置
        ResourceBundle bundle = ResourceBundle.getBundle("properties.hbase");
        conf.set("hbase.zookeeper.quorum",bundle.getString("hbase.zookeeper.quorum"));
//        conf.set("hbase.zookeeper.quorum","shmaster,shslave1");
        //设置zookeeper连接端口，默认2181
        conf.set("hbase.zookeeper.property.clientPort", bundle.getString("hbase.zookeeper.property.clientPort"));

        configuration = conf;

        return conf;
    }


    //创建连接
    private static void createConnection(Configuration conf) {
        //连接
        try {
            connection = ConnectionFactory.createConnection(conf);
            admin = connection.getAdmin();
        } catch (IOException e) {
            e.printStackTrace();
            //log.error("初始化失败");
            throw new RuntimeException("HBase初始化失败");
        }
    }

    //获得配置
    public Configuration getConfiguration() {
        return configuration;
    }

    //获得连接
    //有时候会出现null,比如分发到spark的子节点上
    public Connection getConnection() {
        //fixme 这里spark只能分发默认配置,如果配置参数不是默认的?比如有多个hbase地址
        if (configuration==null){
            if(connection ==null)
                getInstance();
        }else {
            //fixme 是往这里走,是只有connect丢失?
            getInstance(configuration);
        }

        return connection;
    }


    private HBaseTemplate(){}

    /**
     *  获得单例对象
     *
     * @return     获得单例
     */
    public static HBaseTemplate getInstance() {
        return getInstance(createConfiguration());
    }


    /**
     *  获得真正的配置对象
     *
     * @param conf
     * @return
     */
    public static HBaseTemplate getInstance(Configuration conf) {

        if(instance==null){
            synchronized (HBaseTemplate.class){
                if (instance==null){
                    init(conf);
                    instance = new HBaseTemplate();
                    return instance;
                }
            }
        }

        return instance;
    }


    //不区分大小写,判断表是否存在
    public boolean existTable(String tableName) throws IOException {
        boolean flag = false;
        TableName[] tableNames = admin.listTableNames();
        for (int i=0;i<tableNames.length;i++){
            if (StringUtils.equalsIgnoreCase(tableNames[i].getNameAsString(),tableName))
                flag=true;
        }
        return flag;
    }


    /**
     *      获得hbase的表
     *
     * @param tableName     hbase表名
     * @return
     */
    public Table getTable(String tableName) {

        //获取表t1
        TableName tname=TableName.valueOf(tableName);
        //获取Table
        Table table = null;
        try {

            //fixme 0.98开始,没这个表,第一次使用错误才会报错
            table = getConnection().getTable(tname);

            if (!existTable(tableName))
                throw new RuntimeException("HBase表"+tableName+"不存在");

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("HBase链接失败");
        }

        return table;
    }



    /**
     *      根据hbase的rowKey查询整行数据
     *
     * @param rowKey    hbase字节转换形式的key
     * @return
     */
    public Result getRow(String tableName, byte[] rowKey) {
        //创建get对象
        Get key = new Get(rowKey);
        //根据key获取结果
        Result row = null;
        try {
            row = getTable(tableName).get(key);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("不能获取当前行");
        }

        return row;
    }


    //关闭连接
    public void close(){
        try {
            getConnection().close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("关闭连接");
        }
    }



    //创建表,根据
    public void createTable(String tableName,String family,String... splits) throws IOException {

        HTableDescriptor td = new HTableDescriptor(TableName.valueOf(tableName));
        HColumnDescriptor cd = new HColumnDescriptor(Bytes.toBytes(family));
        td.addFamily(cd);

        byte[] [] byts = new byte[splits.length][];
        for (int i = 0; i < splits.length; i++) {
            byts[i] = splits[i].getBytes();
        }
        try {
            admin.createTable(td,byts);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     *      如果存在
     *
     * @param tableName     HBase的表名
     * @return
     * @throws IOException
     */
    public HTable getHTable(String tableName) throws IOException {

        HTable hTable = getHTableMap().get(tableName);
        if (hTable==null){
            TableName theTableName = TableName.valueOf(tableName);
            hTable = (HTable) getInstance().getConnection().getTable(theTableName);
            hTable.setAutoFlushTo(false);
            getHTableMap().put(tableName,hTable);
        }

        return hTable;
    }

    public static Map<String, HTable> getHTableMap() {
        if (hTableMap==null)
            hTableMap = new ConcurrentHashMap<>();
        return hTableMap;
    }


    //运行spark时候在ThreadLocal下获得HTable,避免序列化问题,而且这样只需要每个Executor的第一个生成HTable的连接
    //多个Table会有顶替的风险,可以用map覆盖

    //那我为什么不从开始就直接用为空判断防止序列化呢
    public HTable getSparkHTable(String tableName) throws IOException {

        HTable hTable = hTableThreadLocal.get();

        if (hTable==null)
            hTableThreadLocal.set(getHTable(tableName));

        return hTableThreadLocal.get();
    }




}
