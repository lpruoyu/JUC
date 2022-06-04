package programmer.lp.sxt.advance.lock;

class ReLock {
    private volatile boolean isLocked; // 锁是否被占用
    private volatile Thread lockedBy; // 被那个线程占用了该锁
    private volatile int holdCount; // 锁计数器

    public synchronized void lock() throws InterruptedException {
        final Thread currentThread = Thread.currentThread();
        while (isLocked && currentThread != lockedBy) {
            wait();
        }

        lockedBy = currentThread;
        isLocked = true;
        holdCount++;
    }

    public synchronized void unlock() throws InterruptedException {
        if (Thread.currentThread() == lockedBy) {
            holdCount--;
            if (holdCount == 0) {
                lockedBy = null;
                isLocked = false;
                notifyAll();
            }
        }
    }
}

class ReUsage {
    private ReLock lock = new ReLock();

    public void a() throws InterruptedException {
        lock.lock();
        b();
        lock.unlock();
    }

    public void b() throws InterruptedException {
        lock.lock();
        // ...
        lock.unlock();
    }
}

// 可重入锁
public class Can {
    public static void main(String[] args) throws InterruptedException {
        final ReUsage reUsage = new ReUsage();
        reUsage.a();
        reUsage.b();
    }
}
