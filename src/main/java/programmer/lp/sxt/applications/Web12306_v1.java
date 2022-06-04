package programmer.lp.sxt.applications;

/*
    模拟买票
 */

class V1 implements Runnable {
    // 票数
    private int tickets = 100;

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

public class Web12306_v1 {
    public static void main(String[] args) {
        V1 resource = new V1();
        new Thread(resource, "码农").start();
        new Thread(resource, "码畜").start();
        new Thread(resource, "码蝗").start();
    }
}
