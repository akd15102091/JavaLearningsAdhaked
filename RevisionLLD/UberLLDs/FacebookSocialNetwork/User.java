package UberLLDs.FacebookSocialNetwork;

public class User {
    private final long id;
    private final String name;

    User(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() { return id; }
    public String getName() { return name; }
}
