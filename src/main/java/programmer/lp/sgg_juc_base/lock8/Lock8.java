package programmer.lp.sgg_juc_base.lock8;

import java.util.concurrent.TimeUnit;

class Phone {
/*    public static synchronized void sendEmail() {
        try {
            TimeUnit.SECONDS.sleep(4); // 暂停4秒
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + "----sendEmail");
    }

    public synchronized void sendSMS() {
        System.out.println(Thread.currentThread().getName() + "----sendSMS");
    }*/
/*    public static synchronized void sendEmail() {
        try {
            TimeUnit.SECONDS.sleep(4); // 暂停4秒
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + "----sendEmail");
    }

    public static synchronized void sendSMS() {
        System.out.println(Thread.currentThread().getName() + "----sendSMS");
    }*/
    public synchronized void sendEmail() {
        try {
            TimeUnit.SECONDS.sleep(4); // 暂停4秒
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + "----sendEmail");
    }

    public synchronized void sendSMS() {
        System.out.println(Thread.currentThread().getName() + "----sendSMS");
    }

    public void hello() {
        System.out.println(Thread.currentThread().getName() + "----hello");
    }
}

/* 多线程8锁问题：
    1 标准访问，先打印短信还是邮件？
    2 邮件方法暂停4秒，先打印短信还是邮件？
    3 新增一个普通（无synchronized）的hello方法，是先打邮件还是hello？
    4 现在有两部手机，一个调用短信方法，一个调用邮件方法，先打印短信还是邮件？
    5 两个静态同步方法，同一部手机，先打印短信还是邮件？
    6 两个静态同步方法，两部手机，一个调用短信方法，一个调用邮件方法，先打印短信还是邮件？
    7 1个静态同步方法，1个普通同步方法，1部手机，先打印短信还是邮件？
    8 1个静态同步方法，1个普通同步方法，2部手机，先打印短信还是邮件？
 */
public class Lock8 {
    public static void main(String[] args) throws Exception {
        Phone phone = new Phone();
        Phone phone2 = new Phone();
//        new Thread(() -> phone.sendEmail(), "A").start();
//        Thread.sleep(200); // 确保sendEmail方法先被执行
//        new Thread(() -> phone.sendSMS(), "B").start();

//        new Thread(() -> phone.hello(), "C").start();
//        new Thread(() -> phone2.sendSMS(), "B").start();
//        new Thread(() -> phone.sendEmail(), "A").start();
//        Thread.sleep(200); // 确保sendEmail方法先被执行
//        new Thread(() -> phone2.sendSMS(), "B").start();
    }
}

/*
一个对象里面如果有多个synchronized方法，
某一个时刻内，只要一个线程去调用其中的一个synchronized方法了，其它的线程都只能等待，
换句话说，某一个时刻内，只能有唯一一个线程去访问这些synchronized方法
一个对象里面的多个synchronized实例方法锁的都是当前对象this，
被锁定后，其它的线程都不能进入到当前对象的其它的synchronized方法

当一个线程试图访问同步代码块时，它首先必须得到锁，退出或抛出异常时必须释放锁。

加个普通方法后和同步锁无关

换成两个对象后，不是同一把锁了

synchronized实现同步的基础：Java中的每一个对象都可以作为锁。
具体表现为以下3种形式。
对于普通同步方法，锁是当前实例对象。
对于静态同步方法，锁是当前类的Class对象。
对于同步方法块，锁是synchronized括号里配置的对象

也就是说如果一个实例对象的非静态同步方法获取锁后，
该实例对象的其他非静态同步方法必须等待获取锁的方法释放锁后才能获取锁，
可是别的实例对象的非静态同步方法因为跟该实例对象的非静态同步方法用的是不同的锁，
所以无须等待该实例对象已获取锁的非静态同步方法释放锁就可以获取他们自己的锁。

所有的静态同步方法用的也是同一把锁——类对象本身，
这两把锁是两个不同的对象（一个是this，一个是class），所以静态同步方法与非静态同步方法之间是不会竞争锁的。
但是一旦一个静态同步方法获取锁后，其他的静态同步方法都必须等待该方法释放锁后才能获取锁
 */