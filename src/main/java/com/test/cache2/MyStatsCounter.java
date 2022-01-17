package com.test.cache2;

import com.github.benmanes.caffeine.cache.stats.CacheStats;
import com.github.benmanes.caffeine.cache.stats.StatsCounter;

public class MyStatsCounter implements StatsCounter {
    @Override
    public void recordHits(int i) {
        System.out.println("命中次数：" + i);
    }

    @Override
    public void recordMisses(int i) {
        System.out.println("未命中次数：" + i);
    }

    @Override
    public void recordLoadSuccess(long l) {
        System.out.println("加载成功次数：" + l);
    }

    @Override
    public void recordLoadFailure(long l) {
        System.out.println("加载失败次数：" + l);
    }

    @Override
    public void recordEviction() {
        System.out.println("因为缓存大小限制，执行了一次缓存清除工作");
    }

    @Override
    public void recordEviction(int weight) {
        System.out.println("因为缓存权重限制，执行了一次缓存清除工作，清除的数据的权重为：" + weight);
    }

    @Override
    public CacheStats snapshot() {
        return null;
    }
}


