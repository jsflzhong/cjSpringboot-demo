package Common;

import java.time.ZonedDateTime;

public class User {

    private int id;

    private String name;

    private ZonedDateTime birthday;

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
}
