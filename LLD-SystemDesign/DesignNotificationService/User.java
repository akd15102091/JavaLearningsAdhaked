package DesignNotificationService;

public class User {
    private final String id;
    private final String name;
    private final UserPreferences preferences;

    public User(String id, String name, UserPreferences preferences) {
        this.id = id;
        this.name = name;
        this.preferences = preferences;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public UserPreferences getPreferences() { return preferences; }
}
