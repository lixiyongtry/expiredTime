package com.test.cache2;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.Expiry;
import com.github.benmanes.caffeine.cache.stats.CacheStats;
import lombok.Data;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Test3 {

    public static void main(String[] args) throws Exception{
        test5();
    }

    public static void test1() throws Exception{
        Caffeine<String, Person> caffeine = Caffeine.newBuilder()
                .maximumWeight(30)
                .expireAfterWrite(getRandomTime(), TimeUnit.SECONDS)
                .weigher((String key, Person value)-> value.getAge());
        Cache<String, Person> cache = caffeine.build();
        cache.put("one", new Person(12, "one"));
        cache.put("two", new Person(18, "two"));
        Thread.sleep(1000);
        System.out.println(cache.getIfPresent("one").getName());
        Thread.sleep(2000);
        System.out.println(cache.getIfPresent("one"));
    }

    public static void test2() {
        Caffeine<String, Person> caffeine = Caffeine.newBuilder()
                .maximumWeight(30)
                .recordStats()
                .weigher((String key, Person value)-> value.getAge());
        Cache<String, Person> cache = caffeine.build();
        cache.put("one", new Person(12, "one"));
        cache.put("two", new Person(18, "two"));
        cache.put("three", new Person(1, "three"));
        cache.getIfPresent("www");
        CacheStats stats = cache.stats();

        System.out.println(stats.hitCount());

    }

    public static void test3() throws Exception{
        MyStatsCounter myStatsCounter = new MyStatsCounter();
        Caffeine<String, Person> caffeine = Caffeine.newBuilder()
                .maximumWeight(30)
                .recordStats(()->myStatsCounter)
                .weigher((String key, Person value)-> value.getAge());
        Cache<String, Person> cache = caffeine.build();
        cache.put("one", new Person(12, "one"));
        cache.put("two", new Person(18, "two"));
        cache.put("three", new Person(1, "three"));
        cache.getIfPresent("ww");
        cache.getIfPresent("one");
        CacheStats stats = myStatsCounter.snapshot();
        Thread.sleep(1000);

    }

    public static void test4() throws Exception{
        Caffeine<String, Person> caffeine = Caffeine.newBuilder()
                .maximumWeight(30)
                .expireAfterAccess(2, TimeUnit.SECONDS)
                .weigher((String key, Person value)-> value.getAge());
        Cache<String, Person> cache = caffeine.build();
        cache.put("one", new Person(12, "one"));
        cache.put("two", new Person(18, "two"));
        Thread.sleep(3000);
        System.out.println(cache.getIfPresent("one"));
        System.out.println(cache.getIfPresent("two"));

    }

    public static void test5() throws Exception{
        MyStatsCounter myStatsCounter = new MyStatsCounter();
        Caffeine<String, Person> caffeine = Caffeine.newBuilder()
//                .maximumWeight(30)
                .recordStats(()->myStatsCounter)
                .expireAfter(new Expiry<String, Person>() {
                    @Override
                    public long expireAfterCreate(String s, Person person, long l) {
                        if(person.getAge() > 60){ //首次存入缓存后，年龄大于 60 的，过期时间为 4 秒
                            return 4000000000L;
                        }
                        return 2000000000L; // 否则为 2 秒
                    }

                    @Override
                    public long expireAfterUpdate(String s, Person person, long l, long l1) {
                        if(person.getName().equals("one")){ // 更新 one 这个人之后，过期时间为 8 秒
                            return 8000000000L;
                        }
                        return 4000000000L; // 更新其它人后，过期时间为 4 秒
                    }

                    @Override
                    public long expireAfterRead(String s, Person person, long l, long l1) {
                        return 3000000000L; // 每次被读取后，过期时间为 3 秒
                    }
                });
//                .weigher((String key, Person value)-> value.getAge());
        Cache<String, Person> cache = caffeine.build();

        cache.put("one", new Person(66, "one"));
        cache.put("two", new Person(18, "two"));
        Person person = cache.getIfPresent("one");
        Thread.sleep(6000);
        cache.getIfPresent("ww");

        System.out.println(person);
        CacheStats stats = myStatsCounter.snapshot();
        Thread.sleep(1000);


    }

    private static int getRandomTime() {
        Random random = new Random();
        return 600 + random.nextInt(60);
    }


}


