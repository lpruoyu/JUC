package programmer.lp.sgg_juc_base.lambda;

/*
Lambda口诀：拷贝小括号    写死右箭头    落地大括号
 */

@FunctionalInterface
interface Foo {
    void fun();
    default void a() {
        System.out.println("a");
    }
    default void b() {
        System.out.println("b");
    }
    static void c() {
        System.out.println("c");
    }
    static void d() {
        System.out.println("d");
    }
}

//@FunctionalInterface
interface Consumer<V> {
    void fun(V v);
}

@FunctionalInterface
interface Producer<S, R> {
    R run(S s);
}

public class LambdaExpress {

    public static void main(String[] args) {
        Foo foo = () -> {
            System.out.println("hhhh");
        };
//        Foo foo = () -> System.out.println("aaaa");
//        foo.fun();
//        foo.a();
        foo = Foo::c;
        foo.a();
        Foo.c();

//        Consumer<String> consumer = v -> System.out.println(v + "----哈哈哈哈");
//        Consumer<String> consumer = System.out::println;
//        consumer.fun("okokok");

//        Producer<String,Integer> producer = (String str)->{return Integer.parseInt(str);};
//        Producer<String, Integer> producer = s -> Integer.parseInt(s) + 100;
//        System.out.println(producer.run("10") + 1);
    }

}
