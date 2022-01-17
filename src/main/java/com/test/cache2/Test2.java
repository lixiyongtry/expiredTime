package com.test.cache2;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.Expiry;
import com.github.benmanes.caffeine.cache.LoadingCache;
import lombok.Data;

import java.util.Random;

public class Test2 {

    public static final LoadingCache<String, DataObject> CONFIG_STRINGS;
    static {
        CONFIG_STRINGS = Caffeine.newBuilder().expireAfter(new Expiry<String, DataObject>() {
            @Override
            public long expireAfterCreate(
                    String key, DataObject value, long currentTime) {
                Long time = getRandomTime();
                System.out.println(time);
                return time;
            }
            @Override
            public long expireAfterUpdate(
                    String key, DataObject value, long currentTime, long currentDuration) {
                return currentDuration;
            }
            @Override
            public long expireAfterRead(
                    String key, DataObject value, long currentTime, long currentDuration) {
                return currentDuration;
            }
        }).build(
                k -> {
                    System.out.println("缓存未命中");
                    return DataObject.get("Data for " + k);
                });
    }

    public static void main(String[] args) {

        DataObject dataObject = CONFIG_STRINGS.get("data");

//        try {
//            Thread.sleep(4000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        DataObject dataObject3 = CONFIG_STRINGS.get("data");
        DataObject dataObject1 = CONFIG_STRINGS.get("data1");
        System.out.println(dataObject);

        CONFIG_STRINGS.stats();
        System.out.println(CONFIG_STRINGS.estimatedSize());
        System.out.println(dataObject.getData());
        System.out.println(dataObject.getData().length());
        System.out.println(dataObject1.getData().length());

    }

    public String getConfigString(String path) {
        return "aa";
    }

    private static Long getRandomTime() {
        Random random = new Random();
        return 600L + random.nextInt(60);
    }
}

@Data
class DataObject {
    private final String data;


    private static int objectCounter = 0;
    // standard constructors/getters

    public static DataObject get(String data) {
        objectCounter++;
        return new DataObject(data);
    }
}

