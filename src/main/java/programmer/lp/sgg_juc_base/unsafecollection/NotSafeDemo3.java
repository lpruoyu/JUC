package programmer.lp.sgg_juc_base.unsafecollection;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class NotSafeDemo3 {
    public static void main(String[] args) {
//        Map<String, String> map = new HashMap<>();
//        解决方案：
//        Map<String, String> map = Collections.synchronizedMap(new HashMap<>());
        Map<String, String> map = new ConcurrentHashMap<>();
        for (int i = 1; i <= 30; i++) {
            new Thread(() -> { // 让多个线程同时对容器进行读、写操作
                map.put(Thread.currentThread().getName(), UUID.randomUUID().toString());
                System.out.println(map);
            }, String.valueOf(i)).start();
        }
    }
}
