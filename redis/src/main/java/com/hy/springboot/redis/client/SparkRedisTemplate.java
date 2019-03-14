package com.hy.springboot.redis.client;

import redis.clients.jedis.Jedis;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;

/**
 * @user yang he
 * @date old
 * @introduce              第一版RedisTemplate,要求:简单实现spark和redis之间的交流;缺点:没有锁,未完成一致性
 **/
public class SparkRedisTemplate implements Serializable {


    //todo 最关键的还是性能

    /*
            redis保存的规范,redis主要控制key的规范来保存数据
     */
    //库前缀,一般不使用,只是头次插入会添加,但是添加了也只是提醒有这个表,不影响操作
    //或者取消获得由多少表的行为,不然还有去dataBase判断有无此表
    //public static String REDIS_DATABASE_PREFIX="redis_database_";
    //索引表前缀
    //public static String REDIS_TABLE_PREFIX="redis_table_";
    //字段前缀
    public static String REDIS_COLUMN_PREFIX="redis_column_";
    //二级索引前缀
    public static String REDIS_INDEX_PREFIX="redis_index";


    //fixme 如果我知道这个字段和这个表名,就可以定位到这个key了,不需要二级索引啊
    //fixme hbase需要二级索引是因为,定位key快,我表的列里面也是key,我条件搜索就快,但是范围查询反而慢
    //fixme 如果redis需要的情况是,也是需要不同字段来快速确定这个表,而且因为存的直接是: 表名_col+key,范围查询也快

    //fixme 尽量不使用模糊查询
    //fixme 批量插入如何设计管道

    //fixme 索引需要在指定类中特殊查询,暂时不耦合到内存,索引类似于附加

    //fixme 难道要用list或者map达到管理表的效果

    private static SparkRedisTemplate instance;
    //对redis的连接
    private static Jedis connection;


    //私有化原有默认接口
    private SparkRedisTemplate(){}

    //初始化
    private void init(){
        connection = new Jedis("172.16.1.135",6379);
    }


    //单例
    public static SparkRedisTemplate getInstance() {

        if(instance==null){
            synchronized (SparkRedisTemplate.class){
                if (instance==null){
                    instance = new SparkRedisTemplate();
                    return instance;
                }
            }
        }

        return instance;
    }


    //todo 线程池


    //fixme 使用时会有静态丢失问题

    //连接调用
    public Jedis getConnection() {
        if (connection ==null)
            getInstance();
        return connection;
    }



    /*
            单个crud ,其中cud先统一都为insert

     */

    //fixme 索引是只能指定一个吗,怎么确认表有哪些索引,并不删除它


    //插入字段,并添加索引
    //todo 未来使用lua脚本插入
    //todo 或者乐观锁保持事务
    public boolean insertColumnWithIndex(String tableName,String objid,String column,String value){

        //插入表
        insertColumnBasic(tableName,objid,column,value);
        //添加或覆盖二级索引
        insertIndexKey(tableName,column,value,objid);

        return true;
    }

    //插入一行,添加索引
    public void insertWithIndex(String tableName,String objid,Map<String,String> columnAndValue,String indexColumn){

        //插入表
        insertBasic(tableName,contactKey(tableName,objid),columnAndValue);
        //添加指定的索引


    }


    //通过索引获得字段
    public Set<String> getValueByColumnIndex(String tableName,String columnName,String value){
        return getIndexKey(tableName,columnName,value);
    }



    //基本单条插入
    private void insertBasic(String tableName , String objid, Map<String,String> columnAndValue ){
        getConnection().hmset(contactKey(tableName,objid),columnAndValue);
    }

    //基本单条字段插入
    private void insertColumnBasic(String tableName,String objid,String columnName,String value){
        getConnection().hset(contactKey(tableName,objid),columnName,value);
    }


    //插入二级索引
    private void insertIndexKey(String tableName ,String columnName,String value,String objid){
        getConnection().hset(contactIndexKey(tableName, columnName, value),objid,"1");
    }

    //获得二级索引的字段
    private Set<String> getIndexKey(String tableName ,String columnName,String value){
        return getConnection().hkeys(contactIndexKey(tableName, columnName, value));
    }

    //删除二级索引
    private void deleteIndexKey(String tableName ,String columnName,String value,String objid){
        getConnection().hdel(contactIndexKey(tableName, columnName, value),objid);
    }




    /*
                批量crud
     */


    //批量管道插入
    public void insertPipelineBatch(){

    }



    /*
            常用方法
     */

    //拼接索引key
    private String contactIndexKey(String tableName ,String columnName,String value){
        return REDIS_COLUMN_PREFIX+"_"+tableName.toLowerCase()+"_"+columnName.toLowerCase()+"_"+value;
    }


    //拼接字段key
    private String contactKey(String tableName ,String key){
        return REDIS_INDEX_PREFIX+"_"+tableName.toLowerCase()+"_"+key;
    }



}
