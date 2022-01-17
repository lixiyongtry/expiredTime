package com.test.cache2;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.Expiry;
import com.github.benmanes.caffeine.cache.stats.CacheStats;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * https://blog.csdn.net/dgh112233/article/details/118915259?ops_request_misc=&request_id=&biz_id=102&utm_term=caffeine%E6%9F%A5%E7%9C%8B%E8%BF%87%E6%9C%9F%E6%97%B6%E9%97%B4&utm_medium=distribute.pc_search_result.none-task-blog-2~all~sobaiduweb~default-5-118915259.first_rank_v2_pc_rank_v29&spm=1018.2226.3001.4187
 */
public class Test4 {

    public static final Cache<String, String> CONFIG_STRINGS;
    public static MyStatsCounter myStatsCounter = new MyStatsCounter();
    public static Long configTime=0L;

    static {

        CONFIG_STRINGS = Caffeine.newBuilder()
//                .maximumWeight(30)
                .recordStats(()->myStatsCounter)
                .expireAfter(new Expiry<String, String>() {
                    @Override
                    public long expireAfterCreate(String s, String str, long l) {
                        return getTime(configTime); // 否则为 2 秒
                    }

                    @Override
                    public long expireAfterUpdate(String s, String str, long l, long l1) {
                        return l1; // 更新其它人后，过期时间为 4 秒
                    }

                    @Override
                    public long expireAfterRead(String s, String str, long l, long l1) {
                        return l1; // 每次被读取后，过期时间为 3 秒
                    }
                }).build();
//                .weigher((String key, Person value)-> value.getAge());
    }



    public static void main(String[] args) throws Exception{

//        CONFIG_STRINGS.put("key1", "vulue1");
//        CONFIG_STRINGS.put("key2", "vulue2");
//
//        String key1 = CONFIG_STRINGS.getIfPresent("key1");
////        Thread.sleep(6000);
        String str = CONFIG_STRINGS.getIfPresent("ww");
        System.out.println("str "+str);
//
//        System.out.println(key1);
//        CacheStats stats = myStatsCounter.snapshot();
//        Thread.sleep(1000);
        System.out.println(TimeUnit.SECONDS.toNanos(5));
        System.out.println(getStr("key1", 5L));

        System.out.println(getStr("key1"));

    }

    public static String getStr(String key) {
        configTime = 0L;
        String str = CONFIG_STRINGS.getIfPresent(key);
        if (str != null) {
            return str;
        }
        String str1 = getHttp();
        CONFIG_STRINGS.put(key, str1);
        return str1;
    }

    public static String getStr(String key, Long time) {
        configTime = time;
        String str = CONFIG_STRINGS.getIfPresent(key);
        if (str != null) {
            return str;
        }
        String str1 = getHttp();
        CONFIG_STRINGS.put(key, str1);
        return str1;
    }

    public static String getHttp() {
        return "test";
    }

    private static Long getTime(Long time) {
        if (configTime!=0) {
            return TimeUnit.SECONDS.toNanos(time);
        }
        Random random = new Random();
        Long randomTime = 600L + random.nextInt(60);
        return TimeUnit.SECONDS.toNanos(randomTime);
    }
}
