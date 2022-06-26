package programmer.lp.sgg_juc_base.thread_communication;

/*
资源：一个变量：初始值为0
线程：
    A线程对该变量 +1
    B线程对该变量 -1
操作：两个线程交替执行10轮
 */

class AirConditioner { // 资源类
    private int num; // 初始值为0

    public synchronized void increment() {
        if (num != 0) { // 判断
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        // 干活
        num++;
        System.out.println(Thread.currentThread().getName() + "\t" + num);
        notifyAll(); // 通知
    }

    public synchronized void decrement() {
        if (num == 0) { // 判断
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        // 干活
        num--;
        System.out.println(Thread.currentThread().getName() + "\t" + num);
        notifyAll(); // 通知
    }
}

/**
 * 口诀：
 * 1. 高内聚低耦合的情况下：线程操作资源类。（操作就是资源类对外暴露的接口）
 * 2. 线程间通信：判断、干活、通知
 */
public class Test01 {
    public static void main(String[] args) {
        final AirConditioner airConditioner = new AirConditioner();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                airConditioner.increment();
            }
        }, "A").start();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                airConditioner.decrement();
            }
        }, "B").start();
    }
}
