package programmer.lp.sgg_juc_base.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Test1 {
    public static void main(String[] args) {
        final ExecutorService executorService = Executors.newCachedThreadPool();
        Executors.newSingleThreadExecutor();
    }
}
