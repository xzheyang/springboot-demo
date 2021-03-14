package com.hy.springboot.sample.test;

import com.hy.springboot.sample.redis.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @user yang he
 * @date old
 * @introduce
 **/

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class RedisTest {

    @Test
    public void getTest(){
    }


//    @Autowired
//    StringRedisTemplate stringRedisTemplate;
//
//    @Autowired
//    RedisTemplate redisTemplate;
//
//
//
//    @Test
//    public void getTest(){
//
//        List<String> list = new ArrayList<>();
//        for (int i=0;i<10000;i++){
//            list.add(String.valueOf(i));
//        }
//
//        long begin = System.currentTimeMillis();
//
//        //并行流个数
//        System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism","12");
//        //并行流计算
//        list.parallelStream().forEach(s->{
//            String index = stringRedisTemplate.opsForList().index("key_" + s, 2);
//            //System.out.println(index);
//        });
//
//
//
////        for (int i=0;i<10000;i++){
////            //byte[] index = (byte[]) redisTemplate.opsForList().index(("key_" + i).getBytes(), 2);
////            if (i==1)
////                begin = System.currentTimeMillis();
////            String v2 = stringRedisTemplate.opsForList().index("key_"+i, 2);
////            //System.out.println(v2);
////        }
//
//        long end = System.currentTimeMillis();
//        System.out.println("get list used time：" + (end - begin));
//
//
//    }

}
