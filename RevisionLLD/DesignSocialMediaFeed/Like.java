package DesignSocialMediaFeed;

import java.util.UUID;

@SuppressWarnings("unused")
public class Like {
    private String likeId;
    private User likedBy;
    private long likedAt;
    
    public Like(User user){
        this.likeId = UUID.randomUUID().toString();
        this.likedBy = user;
        this.likedAt = System.currentTimeMillis();
    }

    @Override
    public String toString(){
        return likedBy+" has liked on your post";
    }
}
