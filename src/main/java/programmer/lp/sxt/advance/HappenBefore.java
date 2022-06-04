package programmer.lp.sxt.advance;

public class HappenBefore {
    private static int a;
    private static boolean flag;

    public static void main(String[] args) throws InterruptedException {
//        for (int i = 0; i < 1000; i++) {
//            a = 0;
//            flag = false;
        happenBefore();
//        }
    }

    private static void happenBefore() throws InterruptedException {
        Thread t1 = new Thread(() -> {
            a = 1;
            flag = true;
        });
        Thread t2 = new Thread(() -> {
            if (flag) {
                a = 2;
            }
            if (a == 0) {
                System.out.println("Happen before");
            }
        });
        t1.start();
        t2.start();
        t1.join();
        t2.join();
    }
}
