package programmer.lp.sxt.applications;

/*
    龟兔赛跑
 */

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

class RaceX implements Callable<Boolean> {
    private boolean gameOver;

    @Override
    public Boolean call() throws Exception {
        for (int i = 1; i <= 100; i++) { // 每人走100步
            System.out.println(Thread.currentThread().getName() + "走了" + i + "步");
            if (i == 100) {
                gameOver = true;
                return true;
            }
            if (gameOver) break;
        }
        return false;
    }
}

class Race implements Runnable {
    private String winner;

    @Override
    public void run() {
        for (int i = 1; i <= 100; i++) { // 每人走100步
            if (Thread.currentThread().getName().equals("rabbit") && i % 20 == 0) {
                // 兔子喜欢睡觉
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(Thread.currentThread().getName() + "走了" + i + "步");
            if (gameOver(i)) {
                System.out.println(winner + "获胜");
                break; // rabbit若获胜，rabbit的for循环自动结束；这里结束的是tortoise的for循环
            }
        }
    }

    private boolean gameOver(int step) {
        if (winner != null) return true;
        if (step == 100) {
            winner = Thread.currentThread().getName();
            return true;
        }
        return false;
    }
}

public class RabbitAndTortoise {
    public static void main(String[] args) throws Exception {
        RaceX race = new RaceX();
        final ExecutorService executorService = Executors.newFixedThreadPool(2);
        final Future<Boolean> rabbit = executorService.submit(race);
        final Future<Boolean> tortoise = executorService.submit(race);
        System.out.println("rabbit " + rabbit.get());
        System.out.println("tortoise " + tortoise.get());
        executorService.shutdown();
    }
}
