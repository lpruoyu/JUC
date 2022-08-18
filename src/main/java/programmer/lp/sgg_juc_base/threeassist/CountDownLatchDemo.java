package programmer.lp.sgg_juc_base.threeassist;

import java.util.concurrent.CountDownLatch;

/**** 做减法 ****/

/*
    让6个线程先执行完，再执行第7个线程
    （7个人在教室上晚自习，需要让班长最后走，因为他得关窗锁门）

    ========= 类似于倒计时计数器 ========
 */

/*
 * CountDownLatch主要有两个方法，当一个或多个线程调用await方法时，这些线程会阻塞。
 * 其它线程调用countDown方法会将计数器减1(调用countDown方法的线程不会阻塞)，
 * 当计数器的值变为0时，因await方法阻塞的线程会被唤醒，继续执行。
 */

/**
 * @Description:
 *  *让一些线程阻塞直到另一些线程完成一系列操作后才被唤醒。
 *
 * CountDownLatch主要有两个方法，当一个或多个线程调用await方法时，这些线程会阻塞。
 * 其它线程调用countDown方法会将计数器减1(调用countDown方法的线程不会阻塞)，
 * 当计数器的值变为0时，因await方法阻塞的线程会被唤醒，继续执行。
 *
 * 解释：6个同学陆续离开教室后值班同学才可以关门。
 *       main主线程必须要等前面6个线程完成全部工作后，自己才能开干
 */
public class CountDownLatchDemo {
    public static void main(String[] args) throws InterruptedException {
        int members = 6;
        CountDownLatch countDownLatch = new CountDownLatch(members);

        for (int i = 1; i <= members; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + "\t离开了教室");

                countDownLatch.countDown();

            }, String.valueOf(i)).start();
        }

        countDownLatch.await();
        System.out.println("班长" + Thread.currentThread().getName() + "\t关窗锁门并离开了教室");
    }
    public static void main1(String[] args) {
        for (int i = 1; i <= 6; i++) { //6个上自习的同学，各自离开教室的时间不一致
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + "\t离开了教室");
            }, String.valueOf(i)).start();
        }
        System.out.println("班长" + Thread.currentThread().getName() + "\t关窗锁门并离开了教室");
    }
}
