package com.hy.springboot.basic.utils.guava.cache;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.cache.Weigher;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class CaffeineUse {

    /**
     * 设置缓存容量
     * 设置缓存过期策略
     * 设置缓存生成策略
     * 缓存大小和过期策略都是为了解决就是应用内存有限以及缓存有效性的问题。
     * 对于缓存大小有Size和Weight两种模式。
     * Size针对缓存的个数来设置上限。
     *
     * Weight可以通过Weigher函数针对不同的缓存来返回不同Weight，所有缓存累加值不能超过maximumWeight。
     * 当缓存容量超过限制值后，我们就需要根据缓存过期策略淘汰一些缓存。
     * expireAfterAccess会在缓存read或write后指定时间后失效。
     * expireAfterWrite会在缓存write后指定时间后失效。
     *
     * 缓存生成策略通过CacheLoader来封装我们缓存的生成逻辑。我们可以预先初始化缓存，当get的时候，如果key不在缓存中，就会通过CacheLoader来生成我们的缓存。
     *
     */
    public void use(){
        LoadingCache<String,String> cache = CacheBuilder.newBuilder()
                .maximumSize(100)
                .maximumWeight(1000)
                .weigher(new Weigher<String, String>() {
                    @Override
                    public int weigh(String key, String value) {
                        return key.length();
                    }
                })
                .expireAfterAccess(10, TimeUnit.MINUTES)
                .expireAfterAccess(10, TimeUnit.MINUTES)
                .build(new CacheLoader<String, String>() {
                    @Override
                    public String load(String key) throws Exception {
                        return key+"cache";
                    }
                });
        cache.put("test","23333");


        try {
            System.out.println(cache.get("test"));
            System.out.println(cache.get("scj"));
        } catch (ExecutionException e) {
            e.printStackTrace();
            throw new RuntimeException("测试失败不能获取值");
        }

        //output
        //2333
        //scjcache
    }

}
