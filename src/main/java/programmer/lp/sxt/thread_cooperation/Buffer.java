package programmer.lp.sxt.thread_cooperation;

class SteamedBun { // 馒头
    int id;

    public SteamedBun(int id) {
        this.id = id;
    }
}

class Resource {
    SteamedBun[] steamedBuns = new SteamedBun[10]; // 缓冲区——馒头架
    int count; // 馒头架上馒头的数量
    int total; // 今天卖出了多少个馒头

    public synchronized void push(SteamedBun steamedBun) {
        if (null == steamedBun) return;
        if (count == steamedBuns.length) { // 馒头架满了，不能生产，等待消费
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        // 馒头架没满，可以生产
        steamedBuns[count++] = steamedBun;
        // 馒头架上有了馒头，就可以通知消费者消费了
        this.notifyAll();
    }

    public synchronized SteamedBun pop() {
        if (count == 0) { // 馒头架上没有馒头了，不能消费，等待生产
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        // 馒头架上有馒头了，就可以消费了
        SteamedBun steamedBun = steamedBuns[--count];
        // 消费了馒头，就可以通知生产者生产了
        this.notifyAll();
        total++;
        return steamedBun;
    }

}

class Producer implements Runnable { // 馒头店——生产者
    Resource resource;
    boolean outofwork; // 是否下班
    int i; // 生产者生产了多少个馒头
    SteamedBun steamedBun; // 最近生产的馒头

    public Producer(Resource resource) {
        this.resource = resource;
    }

    @Override
    public void run() {
        while (!outofwork) { // 只要没下班就不断的生产馒头
            steamedBun = new SteamedBun(++i);
            resource.push(steamedBun);
        }
    }

    public void outofwork() { // 通知下班，停止生产
        outofwork = true;
        synchronized (resource) {
            resource.notifyAll();
        }
        System.out.println("今天总共生产了" + i + "个馒头；总共卖出了" + resource.total + "个馒头");
        if (steamedBun != null && resource.count > 0
                && resource.steamedBuns[--resource.count] != steamedBun) {
            // steamedBun这个馒头会被浪费掉，看你如何解决了
            // System.out.println(steamedBun);
        }
    }
}

class Consumer implements Runnable {
    Resource resource;
    int howmany; // 每个消费者要消费多少个馒头

    public Consumer(Resource resource, int howmany) {
        this.resource = resource;
        this.howmany = howmany;
    }

    @Override
    public void run() {
        if (howmany < 1) return;
        for (int i = 1; i <= howmany; i++) {
            System.out.println(Thread.currentThread().getName()
                    + "已经消费" + i + "个馒头了；是今天馒头店做的第"
                    + resource.pop().id + "个馒头");
        }
    }
}

// 管程法：缓冲区可以解耦生产者和消费者
public class Buffer {
    public static void main(String[] args) {
        Resource resource = new Resource();
        Producer producer = new Producer(resource);
        new Thread(producer).start();
        new Thread(new Consumer(resource, 20), "高老师").start();
        new Thread(new Consumer(resource, 50), "裴老师").start();
        new Thread(new Consumer(resource, 100), "李老师").start();
        // 让生产者下班
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        producer.outofwork();
    }
}
