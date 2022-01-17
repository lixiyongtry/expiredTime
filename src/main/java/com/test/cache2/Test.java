//package com.test.cache2;
//
//import com.github.benmanes.caffeine.cache.Cache;
//import com.github.benmanes.caffeine.cache.Caffeine;
//import com.github.benmanes.caffeine.cache.Expiry;
//import com.github.benmanes.caffeine.cache.LoadingCache;
//
//import javax.annotation.Nonnull;
//import java.util.concurrent.TimeUnit;
//import java.util.function.Function;
//
//public class Test {
//
//    public static void main(String[] args) {
//        System.out.println(manulOperator("test"));;
//    }
//
//    public static Object manulOperator(String key) {
////        Cache<String, Object> cache = Caffeine.newBuilder()
////                .expireAfterWrite(1, TimeUnit.SECONDS)
////                .expireAfterAccess(1, TimeUnit.SECONDS)
////                .maximumSize(10)
////                .build();
//
//        // 基于不同的到期策略进行退出
//        Long seconds = 600L;
//        LoadingCache<String, Object> cache2 = Caffeine.newBuilder()
//                .expireAfter(new Expiry<String, Object>() {
//                    @Override
//                    public long expireAfterCreate(String key, Object value, long currentTime) {
//                        return TimeUnit.SECONDS.toNanos(seconds);
//                    }
//
//                    @Override
//                    public long expireAfterUpdate(@Nonnull String s, @Nonnull Object o, long l, long l1) {
//                        return 0;
//                    }
//
//                    @Override
//                    public long expireAfterRead(@Nonnull String s, @Nonnull Object o, long l, long l1) {
//                        return 0;
//                    }
//                }).build(key -> function(key));
//        //如果一个key不存在，那么会进入指定的函数生成value
//        Object value = cache2.get(key, t -> setValue(key).apply(key));
//        cache2.put("hello",value);
//
//        //判断是否存在如果不存返回null
//        Object ifPresent = cache2.getIfPresent(key);
//        //移除一个key
//        cache2.invalidate(key);
//        return value;
//    }
//
//    public static Function<String, Object> setValue(String key){
//        return t -> key + "value";
//    }
//}
