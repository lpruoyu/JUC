package programmer.lp.sgg_juc_base.threeassist;

/*
    Semaphore：信号量、信号灯
 */

/*
在信号量上我们定义两种操作：
  acquire（获取） 当一个线程调用acquire操作时，它要么通过成功获取信号量（信号量减1），
         要么一直等下去，直到有线程释放信号量，或超时。
  release（释放）实际上会将信号量的值加1，然后唤醒等待的线程。
信号量主要用于两个目的，一个是用于多个共享资源的互斥使用，另一个用于并发线程数的控制。
 */

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/*
    模拟：7个人要上厕所，但只有3个坑位 【多个线程，多个资源】

    思考一下，假设 Semaphore semaphore = new Semaphore(1); 呢？也就是只有1个坑位呢？ 【多个线程，1个资源】
 */
public class SemaphoreDemo {
    public static void main(String[] args) {
        int ren = 7; // 7个人，7个线程
        int kengwei = 3; // 3个坑位
        Semaphore semaphore = new Semaphore(kengwei);

        for (int i = 1; i <= ren; i++) {
            new Thread(() -> {
                try {
                    semaphore.acquire();

                    System.out.println(Thread.currentThread().getName() + "正在用");
                    TimeUnit.SECONDS.sleep(3);
                    System.out.println(Thread.currentThread().getName() + "用完了");
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    semaphore.release();
                }
            }, String.valueOf(i)).start();
        }
    }
}