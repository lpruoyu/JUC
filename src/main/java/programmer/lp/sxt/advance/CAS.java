package programmer.lp.sxt.advance;

import java.util.concurrent.atomic.AtomicInteger;

public class CAS {
    //库存
    private static AtomicInteger stock = new AtomicInteger(5);

    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                //模拟网络延时
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Integer left = stock.decrementAndGet();
                System.out.println((Thread.currentThread().getName() + "抢了一件商品-->还剩" + left));
                if (left < 1) {
                    System.out.println("抢完了...");
                    return;
                }
            }).start();
        }
    }
}
