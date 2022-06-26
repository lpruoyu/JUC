package programmer.lp.sgg_juc_base.thread_communication;

/*
    防止虚假唤醒
 */
class AirConditioner2 {
    private int num;

    public synchronized void increment() {
        while (num != 0) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        num++;
        System.out.println(Thread.currentThread().getName() + "\t" + num);
        notifyAll();
    }

    public synchronized void decrement() {
        while (num == 0) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        num--;
        System.out.println(Thread.currentThread().getName() + "\t" + num);
        notifyAll();
    }
}

public class Test02 {
    public static void main(String[] args) {
        final AirConditioner2 airConditioner = new AirConditioner2();
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
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                airConditioner.increment();
            }
        }, "C").start();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                airConditioner.decrement();
            }
        }, "D").start();
    }
}
