package programmer.lp.sgg_juc_base.create_thread_callable;

import java.util.concurrent.*;

class A implements Callable<Integer> {
    @Override
    public Integer call() throws Exception {
        TimeUnit.SECONDS.sleep(1);
        return 10;
    }
}

class B implements Callable<Integer> {

    @Override
    public Integer call() throws Exception {
        TimeUnit.SECONDS.sleep(2);
        return 20;
    }
}

class C implements Callable<Integer> {

    @Override
    public Integer call() throws Exception {
        TimeUnit.SECONDS.sleep(3);
        return 30;
    }
}

public class ApplyCallable {
    public static void main1(String[] args) {
        final ExecutorService executorService = Executors.newSingleThreadExecutor();
        final FutureTask<String> future = (FutureTask<String>) executorService.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                System.out.println("被调用");
                return Thread.currentThread().getName() + "\t 哈哈哈哈";
            }
        });
//        System.out.println(future.get());
//        System.out.println(future.get());
//        System.out.println(future.get());
//        System.out.println(future.get());
        executorService.shutdown();
    }

    public static void main(String[] args) throws Exception {
        int result = 0;
        final FutureTask<Integer> a = new FutureTask<>(new A());
        final FutureTask<Integer> b = new FutureTask<>(new B());
        final FutureTask<Integer> c = new FutureTask<>(new C());
        new Thread(a).start();
        new Thread(b).start();
        new Thread(c).start();

        int i = 0;
        { // main线程执行一部分的运算
            TimeUnit.SECONDS.sleep(4);
            i = 40;
        }

        // 汇总结果
        result = i + a.get() + b.get() + c.get();
        System.out.println(result);
    }

}
