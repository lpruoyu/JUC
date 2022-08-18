package programmer.lp.sgg_juc_base.threeassist;

import java.util.concurrent.CyclicBarrier;

/**** 做加法 ****/

/*
 * CyclicBarrier
 * 的字面意思是可循环（Cyclic）使用的屏障（Barrier）。它要做的事情是，
 * 让一组线程到达一个屏障（也可以叫同步点）时被阻塞，
 * 直到最后一个线程到达屏障时，屏障才会开门，所有被屏障拦截的线程才会继续干活。
 * 线程进入屏障通过CyclicBarrier的await()方法。
 */

// 集齐七龙珠方可召唤神龙、人到齐再开会
public class CyclicBarrierDemo {
    public static void main(String[] args) {
        int num = 7;

        //CyclicBarrier(int parties, Runnable barrierAction)
        CyclicBarrier cyclicBarrier = new CyclicBarrier(num, () -> System.out.println(Thread.currentThread().getName() + "召唤神龙"));

        for (int i = 1; i <= num; i++) {
            final int finalI = i;
            new Thread(() -> {
                try {
                    System.out.println(Thread.currentThread().getName() + "收集到了第" + finalI + "颗龙珠");
                    cyclicBarrier.await();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }, String.valueOf(i)).start();
        }
    }
}