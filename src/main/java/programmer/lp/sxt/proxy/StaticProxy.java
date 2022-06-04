package programmer.lp.sxt.proxy;

interface Marry {
    void happyMarry();
}

class You implements Marry {
    @Override
    public void happyMarry() {
        System.out.println("你终于和你心心念念的她结婚了");
    }
}

class WeddingCompany implements Marry {
    Marry target;
    public WeddingCompany(Marry target) {
        this.target = target;
    }
    @Override
    public void happyMarry() {
        before();
        target.happyMarry();
        after();
    }
    private void before() {
        System.out.println("婚庆公司正在为你布置准备");
    }
    private void after() {
    }
}

public class StaticProxy {
    public static void main(String[] args) {
        new WeddingCompany(new You()).happyMarry();
    }
}
