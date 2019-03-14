package com.hy.springboot.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

/**
 * @user yang he
 * @date old
 * @introduce
 **/
public class RedisTest {

    private static final Logger logger = LoggerFactory.getLogger(RedisTest.class);

    public static void main(String[] args) throws Exception {

        //连接本地的 Redis 服务
        Jedis jedis = new Jedis("172.16.1.135",6379);
        //查看服务是否运行
        logger.info("服务正在运行: "+jedis.ping());

        //读取数据
        //getList(jedis);

        //清除所有数据
        //logger.info("清除数据中..."+jedis.flushAll());


        //批量插入数据
        //batchSetUsePipeline(jedis);

        //当前库总量
        logger.info("当前库总量(可能不精确): "+jedis.dbSize());


        //读取10w       6052  13752  7829 11931 57789 12597 ms

        //保存10w       35580 30062
    }


    // 读取数据
    public static void getList(Jedis jedis) throws Exception {

        long begin = System.currentTimeMillis();

        for (int i=0;i<10000;i++){
            jedis.llen(("key_"+i).getBytes());
            //logger.info("11"+ jedis.llen(("key_"+i).getBytes()));

            //List<byte[]> test = jedis.lrange(("key_"+i).getBytes(), 0, -1);

            //for (byte[] s:test){}
            //logger.info(new String(lindex));
        }
        long end = System.currentTimeMillis();

        logger.info("get list used time：" + (end - begin));
    }

    // 批量插入数据到Redis，使用Pipeline
    public static void batchSetUsePipeline(Jedis jedis) throws Exception {
        Pipeline pipelined = jedis.pipelined();
        String keyPrefix = "key";
        long begin = System.currentTimeMillis();
        //插入数据
        for (int i = 0; i < 100000; i++) {
            String key = keyPrefix + "_" + i;
            pipelined.set("key_str_"+i,"ss");
            pipelined.lpush(key.getBytes(),"z1".getBytes(),"z2".getBytes(),"z3".getBytes());
            if (i%10000==0){
                //1w提交一次
                pipelined.sync();
                pipelined.clear();
            }
        }
        //提交最后一次
        pipelined.sync();
        jedis.close();
        long end = System.currentTimeMillis();
        logger.info("use pipeline batch set total time：" + (end - begin));
    }


}
