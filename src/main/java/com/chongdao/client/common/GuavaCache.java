package com.chongdao.client.common;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j
public class GuavaCache {

    //LRU算法
    private static LoadingCache<String,Object> localCache = CacheBuilder.newBuilder().initialCapacity(1000).maximumSize(10000).expireAfterAccess(10, TimeUnit.MINUTES)
            .build(new CacheLoader<String, Object>() {
                //默认的数据加载实现,当调用get取值的时候,如果key没有对应的值,就调用这个方法进行加载.
                @Override
                public Object load(String s) throws Exception {
                    return null;
                }
            });

    public static void setKey(String key,Object value){
        localCache.put(key,value);
    }

    public static Object getKey(String key){
        Object value = null;
        try {
            value = localCache.get(key);
            if("null".equals(value)){
                return null;
            }
            return value;
        }catch (Exception e){
            log.error("localCache get error",e);
        }
        return null;
    }
}
