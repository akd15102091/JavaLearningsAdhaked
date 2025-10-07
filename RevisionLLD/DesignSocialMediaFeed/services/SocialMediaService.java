package DesignSocialMediaFeed.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import DesignSocialMediaFeed.Post;
import DesignSocialMediaFeed.User;

@SuppressWarnings("unused")
public class SocialMediaService {
    private UserService userService;
    private PostService postService;
    private FollowService followService;
    private LikeService likeService;
    private CommentsService commentsService;

    private static SocialMediaService instance;

    private SocialMediaService(){
        this.userService = new UserService();
        this.postService = new PostService();
        this.followService = new FollowService();
        this.likeService = new LikeService();
        this.commentsService = new CommentsService();
    }

    public static synchronized SocialMediaService getInstance(){
        if(instance == null){
            instance = new SocialMediaService();
        }
        return instance;
    }

    // register user
    public User registerUser(String userId, String name, String mobile){
        return this.userService.registerUser(userId, name, mobile);
    }

    // follow user
    public void sendRequest(User follower, User following){ // user A sends request to user B
        this.followService.sendFollowRequest(follower, following);
    }

    public void acceptRequest(User follower, User following){ // user A accept request of user B
        this.followService.acceptFriendRequest(following, follower);
    }

    // create post
    public Post createPost(User author, String content, List<String> imageUrls){
        return this.postService.createPost(author, content, imageUrls);
    }

    // getFeed
    public List<Post> getFeed(User user){
        // get all followers
        List<User> followers = this.followService.getFollowers(user);

        List<Post> feedData = new ArrayList<>();
        for(User follower : followers){
            feedData.addAll(this.postService.getPostsByUser(follower));
        }

        Collections.sort(feedData, (a,b) -> Long.compare(b.getCreatedAt(), a.getCreatedAt()));
        return feedData;

        // Note this can be optimized using priorityQueue
    }

    // like on post
    // comment on post
}
