package SocialPlatformLikeFacebook.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import SocialPlatformLikeFacebook.Post;
import SocialPlatformLikeFacebook.User;

public class FeedService {
    Map<String, User> userStore;
    PostService postService;

    public FeedService(Map<String, User> userStore, PostService postService) {
        this.userStore = userStore;
        this.postService = postService;
    }

    public List<Post> getNewsFeed(String userId) {
        User user = userStore.get(userId);
        List<Post> feed = new ArrayList<>();
        for (String friendId : user.friends) {
            feed.addAll(postService.getPostsByUser(friendId));
        }
        feed.addAll(postService.getPostsByUser(userId));
        feed.sort((a, b) -> b.timestamp.compareTo(a.timestamp));
        return feed;
    }
}
