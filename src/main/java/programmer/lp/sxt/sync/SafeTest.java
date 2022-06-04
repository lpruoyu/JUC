package programmer.lp.sxt.sync;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

//==== 线程安全：卖票
class SafeWeb12306 implements Runnable {
    // 票数
    private int tickets = 20;
    private boolean stop;

    @Override
    public void run() {
        while (!stop) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            sale();
        }
    }

    private synchronized void sale() {
        if (tickets < 1) {
            stop = true;
            return;
        }
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + "卖了第" + tickets + "张票");
        tickets--;
    }
}

//==== 线程安全：取钱
class SafeAccount {
    private String name; // 账户名称
    private volatile int money; // 账户剩余钱数

    public SafeAccount(String name, int money) {
        this.name = name;
        this.money = money;
    }

    public void draw(int take) { // take：要取多少钱
        if (take < 1 || money < 1) return; // 提高性能，细节
        if (money < take) {
            System.out.println(Thread.currentThread().getName() + "想取" + take + "万元；但是"
                    + name + "账户只剩" + money + "万元；取钱失败");
            return;
        }
        // 减少锁的粒度
        synchronized (this) {
            // double check : 考虑临界值
            if (money < take) {
                System.out.println(Thread.currentThread().getName() + "想取" + take + "万元；但是"
                        + name + "账户只剩" + money + "万元；取钱失败");
                return;
            }
            try {
                Thread.sleep(100); // 模拟网络延迟
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            money -= take;
            System.out.println(Thread.currentThread().getName() + "取了" + take + "万元；"
                    + name + "账户还剩" + money + "万元");
        }
    }
}

class SafeDrawing extends Thread {
    private SafeAccount account; // 在哪个账户取钱
    private int takeMoney; // 取多少钱

    public SafeDrawing(SafeAccount account, String name, int takeMoney) {
        super(name);
        this.account = account;
        this.takeMoney = takeMoney;
    }

    @Override
    public void run() {
        account.draw(takeMoney);
    }
}

//==== 线程安全：操作容器
class SafeManipulateCollection {
    public static void manipulateCollection() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            new Thread(() -> {
                synchronized (list) {
                    list.add(Thread.currentThread().getName());
                }
            }).start();
        }
//        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
//        for (int i = 0; i < 10000; i++) {
//            new Thread(() -> list.add(Thread.currentThread().getName())).start();
//        }
        try {
            Thread.sleep(10000); // 确保上面的那些线程都执行完了
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(list.size());
    }
}

public class SafeTest {

    public static void main(String[] args) {
//        saleTicket();
//        takeMoney();
        SafeManipulateCollection.manipulateCollection();
    }

    private static void takeMoney() {
        SafeAccount account = new SafeAccount("结婚礼金", 100);
        new SafeDrawing(account, "可悲的你", 80).start();
        new SafeDrawing(account, "Happy的她", 50).start();
    }

    public static void saleTicket() {
        SafeWeb12306 resource = new SafeWeb12306();
        new Thread(resource, "码农").start();
        new Thread(resource, "码畜").start();
        new Thread(resource, "码蝗").start();
    }

}
