package programmer.lp.sgg_juc_base.stream;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@NoArgsConstructor
@Data
@Accessors(chain = true)
public class Book {
    private Integer id;
    private String name;

    public static void main(String[] args) {
        Book b1 = new Book();
        b1.setId(1);
        b1.setName("西游记");

        Book b2 = new Book().setId(2).setName("美猴王");
    }
}
