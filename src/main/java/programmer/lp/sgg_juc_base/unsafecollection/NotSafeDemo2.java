package programmer.lp.sgg_juc_base.unsafecollection;

import java.util.*;
import java.util.concurrent.CopyOnWriteArraySet;

public class NotSafeDemo2 {
    public static void main(String[] args) {
//        Set<String> set = new HashSet<>();
//        解决方案：
//        Set<String> set = Collections.synchronizedSet(new HashSet<>());
        Set<String> set = new CopyOnWriteArraySet<>();
        for (int i = 1; i <= 30; i++) {
            new Thread(() -> {
                set.add(UUID.randomUUID().toString());
                System.out.println(set);
            }, String.valueOf(i)).start();
        }
    }
}
