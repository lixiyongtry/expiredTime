package com.test.cache1;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public final class ConfigCache {

    private static MyConfig myConfig = null;

    private ConfigCache() {
    }

    static {
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(() -> {
            myConfig = null;
        }, 0, 60, TimeUnit.SECONDS);
    }

    public static MyConfig get() {
        return myConfig;
    }

    public static void put(MyConfig newConfig) {
        myConfig = newConfig;
    }

    public MyConfig getConfig(){
        MyConfig myConfig = ConfigCache.get();
        if(myConfig == null){
//            myConfig = JSONObject.parseObject(stringRedisTemplate.opsForValue().get("config"), MyConfig.class);
            ConfigCache.put(myConfig);
        }
        return myConfig;
    }
}
