package programmer.lp.sgg_juc_base.others_advanced;

import java.util.concurrent.CompletableFuture;

public class CompletableFutureDemo {
    public static void main1(String[] args) throws Exception {
        // 去执行一个异步任务，相当于启动一个线程，一般不使用
        CompletableFuture<Void> completableFuture1 = CompletableFuture.runAsync(() -> {
            System.out.println(Thread.currentThread().getName() + "\tcompletableFuture1");
        });
        completableFuture1.get();
    }

    public static void main2(String[] args) throws Exception {
        //异步回调
        CompletableFuture<Integer> completableFuture2 = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName() + "\tcompletableFuture2");
//            int i = 10/0;
            return 1024;
        });

        final Integer result = completableFuture2
                .whenComplete((t, u) -> { // 只要任务执行完就会回调

                    // whenCompleteAsync与whenComplete的区别在于这儿的线程环境
                    System.out.println(Thread.currentThread().getName() + "\twhenComplete");

                    // 任务执行成功，t就是任务成功执行的结果
                    // 任务执行失败，t就是null
                    System.out.println("-------t=" + t);
                    // 任务执行成功，u就是null
                    // 任务执行失败，u就是异常信息
                    System.out.println("-------u=" + u);
                }).exceptionally(f -> { // 任务执行失败才回调
                    System.out.println("-------exception:" + f.getMessage());
                    return 4444;
                }).get();

        System.out.println(result); // 不管任务是否执行成功都可以拿到一个返回值
    }

    public static void main3(String[] args) throws Exception {
        //异步回调
        CompletableFuture<Integer> completableFuture3 = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName() + "\tcompletableFuture3");
//            int i = 10/0;
            return 1024;
        });

        final Integer result = completableFuture3
                .whenCompleteAsync((t, u) -> { // 只要任务执行完就会回调

                    // whenCompleteAsync与whenComplete的区别在于这儿的线程环境
                    System.out.println(Thread.currentThread().getName() + "\twhenCompleteAsync");

                    // 任务执行成功，t就是任务成功执行的结果
                    // 任务执行失败，t就是null
                    System.out.println("-------t=" + t);
                    // 任务执行成功，u就是null
                    // 任务执行失败，u就是异常信息
                    System.out.println("-------u=" + u);
                }).exceptionally(f -> { // 任务执行失败才回调
                    System.out.println("-------exception:" + f.getMessage());
                    return 4444;
                }).get();

        System.out.println(result); // 不管任务是否执行成功都可以拿到一个返回值
    }

    public static void main(String[] args) throws Exception {
        CompletableFuture<Integer> completableFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName() + "\tfuture");
            return 1024;
        });
        completableFuture
                .whenComplete((t, u) -> {
                    System.out.println(Thread.currentThread().getName() + "\twhenComplete");
                })
                .exceptionally(f -> {
                    return 4444;
                }).get();

        Thread.sleep(2000);

        new Thread(() -> {

            CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
                System.out.println(Thread.currentThread().getName() + "\tfuture");
                return 1024;
            });
            try {
                future
                        .whenComplete((t, u) -> {
                            System.out.println(Thread.currentThread().getName() + "\twhenComplete");
                        })
                        .exceptionally(f -> {
                            return 4444;
                        }).get();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }, "线程a").start();

        Thread.sleep(2000);

        new Thread(() -> {

            CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
                System.out.println(Thread.currentThread().getName() + "\tfuture");
                return 1024;
            });
            try {
                future
                        .whenCompleteAsync((t, u) -> {
                            System.out.println(Thread.currentThread().getName() + "\twhenCompleteAsync");
                        })
                        .exceptionally(f -> {
                            return 4444;
                        }).get();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }, "线程b").start();
    }
}