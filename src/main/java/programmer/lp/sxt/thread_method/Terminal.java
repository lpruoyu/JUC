package programmer.lp.sxt.thread_method;

class FootballMatch implements Runnable {
    private boolean stop;

    @Override
    public void run() {
        while (!stop) {
            System.out.println(Thread.currentThread().getName() + "线程正在运行");
        }
    }

    public void terminal() {
        stop = true;
    }
}

public class Terminal {

    public static void main(String[] args) throws InterruptedException {
        final FootballMatch match = new FootballMatch();
        final Thread thread = new Thread(match);
        thread.start();
        for (int i = 0; i < 100; i++) {
            System.out.println(Thread.currentThread().getName() + "线程正在运行");
            if (i == 88) {
                match.terminal(); // 停止线程
                System.err.println(thread.getName() + "结束运行");
                break;
            }
        }
    }

}
