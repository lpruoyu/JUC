package programmer.lp.sxt.advance;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

class MyTask extends TimerTask {
    @Override
    public void run() {
        System.out.println("任务被执行");
    }
}

public class TimerTest {
    public static void main(String[] args) {
        Timer timer = new Timer();
//        timer.schedule(new MyTask(), 2000); // 执行一次任务
//        timer.schedule(new MyTask(), 2000, 1000); // 2秒后执行第一次，之后每隔1秒执行一次
        final Date firstTime = new Date(System.currentTimeMillis() + 5000L);
        timer.schedule(new MyTask()
                , firstTime
                , 2000); // 指定时间开始执行，之后每隔2秒执行一次
    }
}
