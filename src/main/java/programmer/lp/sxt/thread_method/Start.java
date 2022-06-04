package programmer.lp.sxt.thread_method;

public class Start {

    public static void main(String[] args) {
        final Thread thread = new Thread() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + "hhhh");
            }
        };
        thread.start();
        thread.start(); // 出现异常
    }

}
