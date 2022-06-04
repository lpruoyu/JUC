package programmer.lp.sxt.thread_method;

class Father extends Thread {
    @Override
    public void run() {
        System.out.println("老爸没烟抽了，让孩子去买烟");
        final Son son = new Son();
        son.start();
        try {
            son.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("老爸接过烟去抽了");
    }
}

class Son extends Thread {
    @Override
    public void run() {
        System.out.println("儿子接过钱去买烟了");
        System.out.println("儿子路上遇到了个游戏厅，进入玩了一会儿");
        for (int i = 1; i < 11; i++) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(i + "秒过去了");
        }
        System.out.println("儿子想起来要去买烟");
        System.out.println("儿子买好了烟，拿着烟回家了");
    }
}

public class Join {

    public static void main(String[] args) {
        System.out.println("老爸和儿子买烟的故事");
        new Thread(new Father()).start();
    }

}
