package programmer.lp.sxt.advance;

// volatile：保证可见性

public class Volatile {
//    public static int num;
    public volatile static int num;

    public static void main(String[] args) {
        new Thread(() -> {
            while (num == 0) { // 此处不要编写代码，要让这个线程忙的不可开交
            }
        }).start();


        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        num = 1;
    }
}
