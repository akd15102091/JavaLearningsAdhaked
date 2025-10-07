package DesignMeetingSchedular;

import java.time.LocalDateTime;
import java.util.Objects;

@SuppressWarnings("unused")
public class User {
    private final String userId;
    private final String name;

    public User(String userId, String name) {
        this.userId = Objects.requireNonNull(userId, "userId cannot be null");
        this.name = Objects.requireNonNull(name, "name cannot be null");
    }

    public String getUserId() { return userId; }
    public String getName() { return name; }

    @Override
    public String toString() {
        return name + "(" + userId + ")";
    }
}
