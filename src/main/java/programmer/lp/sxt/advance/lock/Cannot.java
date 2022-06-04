package programmer.lp.sxt.advance.lock;

class Lock {
    private volatile boolean isLocked; // 锁是否被占用

    public synchronized void lock() throws InterruptedException {
        while (isLocked) { // 如果锁已经被占用了
            wait();
        }
        // 如果还没有占用该锁
        isLocked = true;
    }

    public synchronized void unlock() {
        isLocked = false;
        notify();
    }
}

class Usage {
    Lock lock = new Lock();

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

// 不可重入锁
public class Cannot {
    public static void main(String[] args) throws InterruptedException {
        new Usage().a();
    }
}
