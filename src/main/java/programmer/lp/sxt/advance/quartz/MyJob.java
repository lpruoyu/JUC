package programmer.lp.sxt.advance.quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;

import java.util.Date;

public class MyJob implements Job {
    @Override
    public void execute(JobExecutionContext context) {
        System.out.println("Hello World\t" + new Date());
    }
}