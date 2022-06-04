package programmer.lp.sxt.thread_cooperation;

class TV {
    String voice;
    boolean flag; // 规定，F：演员表演 T：观众观看

    // 演员表演 观众等待
    public synchronized void play(String voice) {
        if (flag) { // 观众正在观看，需要等待
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        // 观众看完了，再给表演
        this.voice = voice;
        // 切换信号灯
        flag = !flag;
        // 表演完毕，通知观众观看
        this.notifyAll();
    }

    // 观众观看 演员等待
    public synchronized void watch() {
        if (!flag) { // 演员正在表演，需要等待
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        // 演员表演完了，就可以观看了
        System.out.println("观众观看：" + voice);
        // 切换信号灯
        flag = !flag;
        // 观看完毕，通知演员表演
        this.notifyAll();
    }

}

// 生产者——演员
class Player extends Thread {
    TV tv;

    public Player(TV tv) {
        this.tv = tv;
    }

    @Override
    public void run() {
        for (int i = 1; i <= 20; i++) {
            tv.play("第" + i + "场");
            System.out.println("演员正在表演第" + i + "场");
        }
    }
}

// 消费者——观众
class Watcher extends Thread {
    TV tv;

    public Watcher(TV tv) {
        this.tv = tv;
    }

    @Override
    public void run() {
        try { // 确保演员已经表演过了
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (int i = 1; i <= 20; i++) {
            tv.watch();
        }
    }
}

// 信号灯法
public class Signal {
    public static void main(String[] args) {
        TV tv = new TV();
        new Player(tv).start();
        new Watcher(tv).start();
    }
}
