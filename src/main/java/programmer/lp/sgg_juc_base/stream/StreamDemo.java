package programmer.lp.sgg_juc_base.stream;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Arrays;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Accessors(chain = true)
class User {
    private Integer id;
    private Integer age;
    private String name;
}

public class StreamDemo {
    /*
        找出：
            偶数ID + age > 24 + 用户名转为大写 + 用户名倒着排序 + 只输出用户名 + 只输出一个用户名
     */
    public static void main(String[] args) {
//        List<String> list = new ArrayList<>();
//        String[] strs = new String[10];
//        // list ——> array
//        final String[] strings1 = list.toArray(new String[list.size()]);
//        // array ——> list
//        final List<String> list1 = Arrays.asList(strs);

        Arrays.asList(
                new User(11, 23, "a"),
                new User(12, 24, "b"),
                new User(13, 22, "c"),
                new User(14, 28, "d"),
                new User(16, 26, "e")
        )
                .stream()
//                .filter(user -> user.getAge() > 24 && (user.getId() % 2 == 0))
                .filter(user -> user.getAge() > 24)
                .filter(user -> user.getId() % 2 == 0)
                .map(user -> user.getName().toUpperCase())
                .sorted((name1, name2) -> name2.compareTo(name1))
                .limit(1)
                .forEach(System.out::println);
    }
}
