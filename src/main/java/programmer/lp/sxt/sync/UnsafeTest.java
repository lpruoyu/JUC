package programmer.lp.sxt.sync;

import java.util.ArrayList;
import java.util.List;

//==== 线程不安全：卖票
class Web12306 implements Runnable {
    // 票数
    private int tickets = 10;

    @Override
    public void run() {
        while (true) {
            if (tickets < 1) break;
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "卖了第" + tickets + "张票");
            tickets--;
        }
    }
}

//==== 线程不安全：取钱
class Account {
    private String name; // 账户名称
    private int money; // 账户剩余钱数

    public Account(String name, int money) {
        this.name = name;
        this.money = money;
    }

    public void draw(int take) { // take：要取多少钱
        if (money < take) {
            System.out.println(Thread.currentThread().getName() + "想取" + take + "万元；但是"
                    + name + "账户只剩" + money + "万元；取钱失败");
            return;
        }
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        money -= take;
        System.out.println(Thread.currentThread().getName() + "取了" + take + "万元；"
                + name + "账户还剩" + money + "万元");
    }
}

class Drawing extends Thread {
    private Account account; // 在哪个账户取钱
    private int takeMoney; // 取多少钱

    public Drawing(Account account, String name, int takeMoney) {
        super(name);
        this.account = account;
        this.takeMoney = takeMoney;
    }

    @Override
    public void run() {
        account.draw(takeMoney);
    }
}

//==== 线程不安全：操作容器
class ManipulateCollection {
    public static void manipulateCollection() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            new Thread(() -> list.add(Thread.currentThread().getName())).start();
        }
        try {
            Thread.sleep(10000); // 确保上面的那些线程都执行完了
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(list.size());
    }
}

public class UnsafeTest {

    public static void main(String[] args) {
//        saleTicket();
//        takeMoney();
        ManipulateCollection.manipulateCollection();
    }

    private static void takeMoney() {
        Account account = new Account("结婚礼金", 100);
        new Drawing(account, "可悲的你", 80).start();
        new Drawing(account, "Happy的她", 50).start();
    }

    public static void saleTicket() {
        Web12306 resource = new Web12306();
        new Thread(resource, "码农").start();
        new Thread(resource, "码畜").start();
        new Thread(resource, "码蝗").start();
    }

}
