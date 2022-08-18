package programmer.lp.sgg_juc_base.unsafecollection;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

/*写时复制：
CopyOnWrite容器即写时复制的容器。往一个容器添加元素的时候，不直接往当前容器Object[]添加，
而是先将当前容器Object[]进行Copy，复制出一个新的容器Object[] newElements，
然后向新的容器Object[] newElements里添加元素。
添加元素后，再将原容器的引用指向新的容器setArray(newElements)。
这样做的好处是可以对CopyOnWrite容器进行并发的读，而不需要加锁，因为当前容器不会添加任何元素。
所以CopyOnWrite容器也是一种读写分离的思想，读和写不同的容器。
*/

public class NotSafeDemo {
    public static void main2(String[] args) {
//        List<String> list = new ArrayList<>();

//        解决方案
//        List<String> list = new Vector<>();
//        List<String> list = Collections.synchronizedList(new ArrayList<>());
        List<String> list = new CopyOnWriteArrayList<>();
        for (int i = 1; i <= 30; i++) {
            new Thread(() -> { // 让多个线程同时对容器进行读、写操作
                list.add(UUID.randomUUID().toString());
                System.out.println(list);
            }, String.valueOf(i)).start();
        }
    }

    public static void main1(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("A");
        list.add("B");
        list.add("C");
        for (String s : list) {
            System.out.println(s);
        }
    }
}
