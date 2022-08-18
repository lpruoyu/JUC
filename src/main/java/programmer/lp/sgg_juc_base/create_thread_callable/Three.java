package programmer.lp.sgg_juc_base.create_thread_callable;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

class MyCallable implements Callable<String> {
    @Override
    public String call() throws InterruptedException {
        Thread.sleep(1000);
        System.out.println("异步任务执行中...");
        TimeUnit.SECONDS.sleep(1);
        System.out.println("异步任务执行完毕");
        return "执行成功！执行结果:" + Thread.currentThread().getName();
    }
}

/*
FutureTask：未来的任务，可以看做是一个异步任务

主线程中需要执行比较耗时的操作，但又不想阻塞主线程时，可以把这些任务交给FutureTask对象让其在后台完成

当主线程将来需要时，就可以通过Future对象获得后台作业的计算结果或者执行状态。

get方法可以执行多次，也即任务的执行结果可以获取多次，但，任务只会执行一次

第一次调用get方法会阻塞等待FutureTask执行完任务，也就是说，仅在计算完成后才能检索结果，如果计算尚未完成，则阻塞 get 方法。

因此一般将耗时任务丢给FutureTask即可，主线程（工作线程）之后想要使用执行结果时再去get即可
 */

public class Three {
    public static void main(String[] args) {
        MyCallable myCallable = new MyCallable();
        FutureTask<String> futureTask = new FutureTask<>(myCallable);
        new Thread(futureTask).start(); // 一个异步任务只会执行一次
        new Thread(futureTask).start();

        // 这是两个异步任务
//        new Thread(new FutureTask<>(myCallable)).start();
//        new Thread(new FutureTask<>(myCallable)).start();
    }

    public static void main1(String[] args) throws Exception {
        FutureTask<String> futureTask = new FutureTask<>(new MyCallable()); // 创建一个异步任务
        new Thread(futureTask, "A").start(); // 让该异步任务去后台执行，不要妨碍主工作线程

        { // 主工作线程的工作
            System.out.println("main thread begin work 1.");
            TimeUnit.SECONDS.sleep(5);
            System.out.println("main thread end work 1.");
        }

        // 主工作线程需要用到异步任务的计算结果了
        System.out.println(futureTask.get()); // 想要使用异步任务的计算结果时就去调用它的get方法，get方法在执行完成之前会阻塞

        { // 主工作线程的工作
            System.out.println("main thread begin work 2.");
            System.out.println("main thread end work 2.");
        }

        System.out.println(futureTask.get()); // 异步任务的计算结果可以通过get方法获取多次
        System.out.println(futureTask.get());
        System.out.println(futureTask.get());
    }

    /*
    public static void main(String[] args) throws Exception {
        final ExecutorService executorService = Executors.newFixedThreadPool(10);
        final Future<String> result = executorService.submit(new MyCallable());
        System.out.println(result.get());
        executorService.shutdown();
    }
     */
}
