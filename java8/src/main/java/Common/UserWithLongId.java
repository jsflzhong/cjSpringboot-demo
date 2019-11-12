package Common;

import java.time.ZonedDateTime;

public class UserWithLongId {

    private Long id;

    private String name;

    private ZonedDateTime birthday;

    public ZonedDateTime getBirthday() {
        return birthday;
    }

    public UserWithLongId setBirthday(ZonedDateTime birthday) {
        this.birthday = birthday;
        return this;
    }

    public Long getId() {
        return id;
    }

    public UserWithLongId setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public UserWithLongId setName(String name) {
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
}
