package programmer.lp.sxt.advance.quartz;

import static org.quartz.DateBuilder.evenSecondDateAfterNow;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.Date;

public class QuartzTest {
    public static void main(String[] args) throws Exception {
        SchedulerFactory factory = new StdSchedulerFactory();
        Scheduler scheduler = factory.getScheduler();
        JobDetail job = newJob(MyJob.class).withIdentity("job1", "group1").build();
        Date runTime = evenSecondDateAfterNow();
        Trigger trigger = newTrigger().withIdentity("trigger1", "group1").startAt(runTime).build();
        scheduler.scheduleJob(job, trigger);
        scheduler.start();

        try { // 5秒后停止
            Thread.sleep(5L * 1000L);
        } catch (Exception e) {
            e.printStackTrace();
        }
        scheduler.shutdown(true);
    }
}
