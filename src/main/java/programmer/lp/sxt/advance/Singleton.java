package programmer.lp.sxt.advance;

public final class Singleton {
    private Singleton() {
    }
    private volatile static Singleton INSTANCE;

    public static Singleton getInstance() {
        if (INSTANCE == null) {
            synchronized (Singleton.class) {
                if (INSTANCE == null) {
                    INSTANCE = new Singleton();
                    // 创建对象：1、开辟空间；2、初始化对象信息；3、返回对象的地址给引用
                    // 这期间有可能发生指令重排
                    // 因此需要加上volatile
                }
            }
        }
        return INSTANCE;
    }
}
