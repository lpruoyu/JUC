package programmer.lp.sgg_juc_base.saleticket;

/*
题目：3个售票员               售卖                     100张票

多线程编程的企业级套路+模板：
前提：在高内聚低耦合的情况下（高内聚：一个模块应尽量独立的干好自己该干的一件事；低耦合：模块与模块之间尽量减少依赖）
方法：   线程         操作（资源类对外暴露的接口）       资源类
 */

//class Ticket { // 资源类
//    private volatile int tickets = 100;
//    public synchronized void saleTicket() { // 操作方法（资源类对外的接口）
//        if (tickets > 0) {
//            System.out.printf("%s卖了第 %d 张票，还剩 %d 张\n", Thread.currentThread().getName(), tickets--, tickets);
//        }
//    }
//}

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Ticket { // 资源类
    private Lock lock = new ReentrantLock(); // re：重复；entrant：进入；ReentrantLock：可重入锁
    private volatile int tickets = 100;

    public void saleTicket() { // 操作方法（资源类对外的接口）
        lock.lock();
        try {
            if (tickets > 0) {
                System.out.printf("%s卖了第 %d 张票，还剩 %d 张\n", Thread.currentThread().getName(), tickets--, tickets);
            }
        } finally {
            lock.unlock();
        }
    }
}

public class SaleTicket {
    public static void main(String[] args) {
        Ticket ticket = new Ticket();
        new Thread(() -> { for (int i = 0; i < 40; i++) ticket.saleTicket(); }, "售票员A").start();
        new Thread(() -> { for (int i = 0; i < 40; i++) ticket.saleTicket(); }, "售票员B").start();
        new Thread(() -> { for (int i = 0; i < 40; i++) ticket.saleTicket(); }, "售票员C").start();
    }

    public static void main1(String[] args) {
        Ticket ticket = new Ticket(); // 资源类（100张票）
        new Thread(new Runnable() { // 线程（售票员1）
            @Override
            public void run() {
                for (int i = 0; i < 40; i++) { // 操作（售卖）
                    ticket.saleTicket();
                }
            }
        }, "售票员1").start();
        new Thread(new Runnable() { // 线程（售票员2）
            @Override
            public void run() {
                for (int i = 0; i < 40; i++) { // 操作（售卖）
                    ticket.saleTicket();
                }
            }
        }, "售票员2").start();
        new Thread(new Runnable() { // 线程（售票员3）
            @Override
            public void run() {
                for (int i = 0; i < 40; i++) { // 操作（售卖）
                    ticket.saleTicket();
                }
            }
        }, "售票员3").start();
    }
}
