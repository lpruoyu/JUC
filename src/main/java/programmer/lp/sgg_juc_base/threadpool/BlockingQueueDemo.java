package programmer.lp.sgg_juc_base.threadpool;

import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

class R { // 资源类
    private final BlockingQueue<String> resources =
            new ArrayBlockingQueue<>(10);    // 货架上只能有10个蛋糕

    public String sale() throws InterruptedException { // 给客户卖蛋糕
        return resources.take();
    }

    public void make(String cake) throws InterruptedException { // 让厨师做蛋糕
        resources.put(cake);
    }
}

public class BlockingQueueDemo {
    public static void main(String[] args) {
        R r = new R();
        for (int i = 1; i <= 100; i++) { // 100个人吃蛋糕，1个人可以吃10个蛋糕
            new Thread(() -> {
                for (int j = 0; j < 10; j++) {
                    try {
                        final String sale = r.sale();
                        System.out.println(Thread.currentThread().getName() + "\t吃到了：" + sale);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }, "客户" + i).start();
        }
        for (int i = 1; i <= 100; i++) { // 100个厨师做蛋糕，1个厨师可以做10个蛋糕
            new Thread(() -> {
                for (int j = 0; j < 10; j++) {
                    final String s = UUID.randomUUID().toString();
                    System.out.println(Thread.currentThread().getName() + "\t做了：" + s);
                    try {
                        Thread.sleep(1000);
                        r.make(s);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }, "厨师" + i).start();
        }
    }

    public static void main1(String[] args) throws InterruptedException {

//        List list = new ArrayList();

        BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(3);
        //第一组
//        System.out.println(blockingQueue.add("a"));
//        System.out.println(blockingQueue.add("b"));
//        System.out.println(blockingQueue.add("c"));
//        System.out.println(blockingQueue.element());

        //System.out.println(blockingQueue.add("x"));
//        System.out.println(blockingQueue.remove());
//        System.out.println(blockingQueue.remove());
//        System.out.println(blockingQueue.remove());
//        System.out.println(blockingQueue.remove());

//    第二组
//        System.out.println(blockingQueue.offer("a"));
//        System.out.println(blockingQueue.offer("b"));
//        System.out.println(blockingQueue.offer("c"));
//        System.out.println(blockingQueue.offer("x"));
//        System.out.println(blockingQueue.poll());
//        System.out.println(blockingQueue.poll());
//        System.out.println(blockingQueue.poll());
//        System.out.println(blockingQueue.poll());

//    第三组
//         blockingQueue.put("a");
//         blockingQueue.put("b");
//         blockingQueue.put("c");
//         //blockingQueue.put("x");
//        System.out.println(blockingQueue.take());
//        System.out.println(blockingQueue.take());
//        System.out.println(blockingQueue.take());
//        System.out.println(blockingQueue.take());

//    第四组
//        System.out.println(blockingQueue.offer("a"));
//        System.out.println(blockingQueue.offer("b"));
//        System.out.println(blockingQueue.offer("c"));
//        System.out.println(blockingQueue.offer("a", 3L, TimeUnit.SECONDS));
    }
}