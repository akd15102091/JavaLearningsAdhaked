package DesignSocialMediaFeed;

@SuppressWarnings("unused")
public class FollowRequest {
    private String id;
    private User follower;
    private User following;

    public FollowRequest(User follower, User following){
        this.follower = follower;
        this.following = following;
    }

    public String getFollowRequestId(){
        return this.id;
    }

    public User getFollower(){
        return this.follower;
    }
}
