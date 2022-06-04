package programmer.lp.sxt.advance;

public class ThreadLocalTest {
//    private static final ThreadLocal<String> THREAD_LOCAL = new ThreadLocal<>();
//
//    private static final ThreadLocal<Object> THREAD_LOCAL = new ThreadLocal<Object>() {
//        @Override
//        protected Object initialValue() {
//            return "Hello Thread";
//        }
//    };

    private static final ThreadLocal<Object> THREAD_LOCAL = ThreadLocal.withInitial(() -> "Initial");
    // InheritableThreadLocal：子线程继承父线程中ThreadLocal中的数据
    private static final ThreadLocal<Object> INHERITABLE_THREAD_LOCAL = new InheritableThreadLocal<>();
    public static void main(String[] args) {
        new Thread(() -> {
            call();
        }, "张三").start();
        new Thread(() -> call(), "李四").start();
        new Thread(ThreadLocalTest::call, "王五").start();
        call();
    }

    private volatile static int num;

    private static void call() {
        synchronized (ThreadLocalTest.class) {
            THREAD_LOCAL.set(++num);
        }
        System.out.println(Thread.currentThread().getName() + "：" + THREAD_LOCAL.get());
    }
}
