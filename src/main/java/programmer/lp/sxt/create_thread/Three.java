package programmer.lp.sxt.create_thread;

import java.util.concurrent.*;

class MyThread extends Thread {
    @Override
    public void run() {
        System.out.println("我是RUN" + "\t" + Thread.currentThread().getName());
    }
}

class IThread implements Runnable {
    @Override
    public void run() {
        System.out.println("I am RUN" + "\t" + Thread.currentThread().getName());
    }
}

class MeThread implements Callable<String> {
    @Override
    public String call() throws Exception {
        return "Me RUN" + "\t" + Thread.currentThread().getName();
    }
}

public class Three {

    public static void main(String[] args) throws Exception {
        Thread thread = new MyThread();
        thread.start();

        thread = new Thread(new IThread());
        thread.start();

        final ExecutorService executorService = Executors.newFixedThreadPool(10);
        final Future<String> result = executorService.submit(new MeThread());
        System.out.println(result.get());
        executorService.shutdown();
    }

}
