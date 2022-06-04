package programmer.lp.sxt.sync;

/*
    死锁解决：不要在一个代码快中，持有多个对象的锁
 */

class MakeUp extends Thread {
    private int type;
    public static final Object MIRROR = new Object();
    public static final Object LIPSTICK = new Object();

    public MakeUp(int type, String name) {
        super(name);
        this.type = type;
    }

    @Override
    public void run() {
        if (type == 0) {
            synchronized (MIRROR) {
                System.out.println(getName() + "拿到了镜子，开始照镜子");
                System.out.println(getName() + "照完了镜子，想要涂口红，去找口红");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
//                synchronized (LIPSTICK) {
//                    System.out.println(getName() + "拿到了口红，开始涂口红");
//                }
            }
            synchronized (LIPSTICK) {
                System.out.println(getName() + "拿到了口红，开始涂口红");
            }
        } else {
            synchronized (LIPSTICK) {
                System.out.println(getName() + "拿到了口红，开始涂口红");
                System.out.println(getName() + "涂完了口红，想要照镜子，去找镜子");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
//                synchronized (MIRROR) {
//                    System.out.println(getName() + "拿到了镜子，开始照镜子");
//                }
            }
            synchronized (MIRROR) {
                System.out.println(getName() + "拿到了镜子，开始照镜子");
            }
        }
    }
}

public class DeadLock {
    public static void main(String[] args) {
        new MakeUp(0, "舒畅").start();
        new MakeUp(1, "兔子").start();
    }
}
