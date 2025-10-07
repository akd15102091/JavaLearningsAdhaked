package SocialPlatformLikeFacebook;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class User {
    public String userId;
    public String name;
    public String email;
    public String password;
    public String profilePicture;
    public String bio;
    public List<String> interests;
    public Set<String> friends;

    public User(String name, String email, String password) {
        this.userId = UUID.randomUUID().toString();
        this.name = name;
        this.email = email;
        this.password = password;
        this.friends = new HashSet<>();
        this.interests = new ArrayList<>();
    }
}
