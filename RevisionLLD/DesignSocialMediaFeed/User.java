package DesignSocialMediaFeed;

import java.util.List;

@SuppressWarnings("unused")
public class User {
    private String userId;
    private String name;
    private String mobile;

    public User(String userId, String name, String mobile){
        this.userId = userId;
        this.name = name;
        this.mobile = mobile;
    }

    public String getName(){
        return this.name;
    }
}
