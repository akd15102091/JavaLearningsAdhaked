package SocialPlatformLikeFacebook.services;

import java.util.HashMap;
import java.util.Map;

import SocialPlatformLikeFacebook.User;

public class AuthService {
    private Map<String, User> usersByEmail = new HashMap<>();
    private Map<String, User> usersById = new HashMap<>();
    private static AuthService instance;

    private AuthService() {}

    public static AuthService getInstance() {
        if (instance == null) {
            instance = new AuthService();
        }
        return instance;
    }

    public User register(String name, String email, String password) {
        if (usersByEmail.containsKey(email)) throw new RuntimeException("User already exists");
        User user = new User(name, email, password);
        usersByEmail.put(email, user);
        usersById.put(user.userId, user);
        return user;
    }

    public User login(String email, String password) {
        User user = usersByEmail.get(email);
        if (user == null || !user.password.equals(password)) {
            throw new RuntimeException("Invalid credentials");
        }
        return user;
    }

    public User getUserById(String userId) {
        return usersById.get(userId);
    }
}
