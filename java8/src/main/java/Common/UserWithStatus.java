package Common;

public class UserWithStatus {

    private int id;

    private String name;

    private String status;

    public String getStatus() {
        return status;
    }

    public UserWithStatus setStatus(String status) {
        this.status = status;
        return this;
    }

    public int getId() {
        return id;
    }

    public UserWithStatus setId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public UserWithStatus setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public String toString() {
        return "UserWithStatus{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
