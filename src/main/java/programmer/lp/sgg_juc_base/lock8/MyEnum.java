package programmer.lp.sgg_juc_base.lock8;

public enum MyEnum {
    A {
        @Override
        public void fun() {
        }
    },
    B {
        public void fun() {
            System.out.println("fun is being called");
        }
    };

    public void fun() {
        throw new AbstractMethodError();
    }
}
