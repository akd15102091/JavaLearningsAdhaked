package DesignSocialMediaFeed.services;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import DesignSocialMediaFeed.User;

public class UserService {
    private Map<String, User> users = new ConcurrentHashMap<>();

    public User registerUser(String userId, String name, String mobile){
        if(this.users.containsKey(userId)){
            throw new IllegalArgumentException("User with id " +userId+ "already exist.");
        }

        User user = new User(userId, name, mobile);
        this.users.put(userId, user);
        return user;
    }
}
