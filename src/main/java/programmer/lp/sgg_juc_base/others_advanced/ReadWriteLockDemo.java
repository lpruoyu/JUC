package programmer.lp.sgg_juc_base.others_advanced;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

class MyCache {
    private volatile Map<String, Object> map = new HashMap<>();
    private final ReadWriteLock rwLock = new ReentrantReadWriteLock();
    private final ReentrantReadWriteLock.WriteLock writeLock =
            (ReentrantReadWriteLock.WriteLock) rwLock.writeLock();
    private final ReentrantReadWriteLock.ReadLock readLock =
            (ReentrantReadWriteLock.ReadLock) rwLock.readLock();

    public void put(String key, Object value) {
//        rwLock.writeLock().lock();
        writeLock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "\t 正在写" + key);
            //暂停一会儿线程
            try {
                TimeUnit.MILLISECONDS.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            map.put(key, value);
            System.out.println(Thread.currentThread().getName() + "\t 写完了" + key);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
//            rwLock.writeLock().unlock();
            writeLock.unlock();
        }

    }

    public Object get(String key) {
//        rwLock.readLock().lock();
        readLock.lock();
        Object result = null;
        try {
            System.out.println(Thread.currentThread().getName() + "\t 正在读" + key);
            try {
                TimeUnit.MILLISECONDS.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            result = map.get(key);
            System.out.println(Thread.currentThread().getName() + "\t 读完了" + result);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
//        rwLock.readLock().unlock();
            readLock.unlock();
        }
        return result;
    }
}


/*
写时加锁
读时不用加锁
 */

/*
多个线程同时读一个资源类没有任何问题，所以为了满足并发量，读取共享资源时，多个线程应该可以同时进行。
但是
如果有一个线程想去写共享资源类，就不应该再有其他线程可以对该资源进行读写操作

小总结：
        读——读能共存
        读——写不能共存
        写——写不能共存
 */
public class ReadWriteLockDemo {

    public static void main(String[] args) {
        MyCache myCache = new MyCache();

//        for (int i = 1; i <= 5; i++) {
//            final int num = i;
//            new Thread(() -> {
//                myCache.put(num + "", num + "");
//            }, String.valueOf(i)).start();
//        }

//        for (int i = 1; i <= 5; i++) {
//            final int num = i;
//            new Thread(() -> {
//                myCache.get(num + "");
//            }, String.valueOf(i)).start();
//        }


        for (int i = 1; i <= 10; i++) {
            final int num = i;
            new Thread(() -> {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                myCache.get(num + "");
            }, String.valueOf(i)).start();
        }

        for (int i = 1; i <= 10; i++) {
            final int num = i;
            new Thread(() -> {
                myCache.put(num + "", num + "");
            }, String.valueOf(i)).start();
        }
    }
}
