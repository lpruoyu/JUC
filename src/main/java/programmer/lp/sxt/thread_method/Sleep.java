package programmer.lp.sxt.thread_method;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Sleep {

    public static void main(String[] args) throws InterruptedException {
        // 倒计时10秒
        long second = 1000; // 秒
        long counts = 10; // 多少秒
        long howLong = second * counts; // 10秒
        Date currentTime = new Date();
        long endTime = new Date(currentTime.getTime() + howLong).getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("mm:ss");
        while (currentTime.getTime() < endTime) {
            System.out.println(dateFormat.format(currentTime));
            currentTime.setTime(currentTime.getTime() + second);
            Thread.sleep(second);
        }
    }

}
