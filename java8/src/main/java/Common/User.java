package Common;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;

public class User {

    private int id;

    private String name;

    private ZonedDateTime birthday;

    private BigDecimal price;

    public BigDecimal getPrice() {
        return price;
    }

    public User setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public ZonedDateTime getBirthday() {
        return birthday;
    }

    public User setBirthday(ZonedDateTime birthday) {
        this.birthday = birthday;
        return this;
    }

    public int getId() {
        return id;
    }

    public User setId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public User setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", birthday=" + birthday +
                '}';
    }

    public static void main(String[] args) {
        String a = "";
        String b = null;
        String e = null;

        Optional.ofNullable(a).filter(StringUtils::isNotBlank).ifPresent(c -> System.out.println("1111," + c));
        Optional.ofNullable(b).filter(StringUtils::isNotBlank).ifPresent(c -> System.out.println("2222," + c));

    }
}
