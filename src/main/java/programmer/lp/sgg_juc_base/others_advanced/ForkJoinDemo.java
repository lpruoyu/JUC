package programmer.lp.sgg_juc_base.others_advanced;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

// 从1加到100
// 从0加到100

class ExecuteSumTask extends RecursiveTask<Integer> {
    private static final int JUDGE_VALUE = 10;
    private final int begin;
    private final int end;
    private int result;

    public ExecuteSumTask(int begin, int end) {
        this.begin = begin;
        this.end = end;
    }
    
    /*
        @Override
        protected Integer compute() {
            if((end - begin)<=JUDGE_VALUE){
               for(int i =begin;i <= end;i++){
                    result = result + i;
               }
            }else{
                int middle = (begin + end)/2;
                ExecuteSumTask task01 = new ExecuteSumTask(begin,middle);
                ExecuteSumTask task02 = new ExecuteSumTask(middle+1,end);
                task01.fork();
                task02.fork();
                result =  task01.join() + task02.join();
            }
            return result;
        }
     */

    @Override
    protected Integer compute() {
        if ((end - begin) <= JUDGE_VALUE) {
            for (int i = begin; i <= end; i++) {
                result += i;
            }
            return result;
        }

        int middle = (end + begin) / 2;
        final ForkJoinTask<Integer> task1 = new ExecuteSumTask(begin, middle).fork();
        final ForkJoinTask<Integer> task2 = new ExecuteSumTask(middle + 1, end).fork();
        return task1.join() + task2.join();
    }
}

/**
 * 分支合并例子
 * ForkJoinPool
 * ForkJoinTask
 * RecursiveTask
 */
public class ForkJoinDemo {
    public static void main(String[] args) throws Exception {
        final ForkJoinPool pool = new ForkJoinPool();

        final ForkJoinTask<Integer> task = pool.submit(new ExecuteSumTask(1, 100));

        System.out.println(task.get());

        pool.shutdown();
    }
}
