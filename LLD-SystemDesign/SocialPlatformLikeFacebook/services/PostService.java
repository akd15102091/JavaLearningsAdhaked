package SocialPlatformLikeFacebook.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import SocialPlatformLikeFacebook.Post;

public class PostService {
    Map<String, List<Post>> postsByUser = new HashMap<>();

    public Post createPost(String authorId, String content, List<String> mediaUrls) {
        Post post = new Post(authorId, content, mediaUrls);
        postsByUser.computeIfAbsent(authorId, k -> new ArrayList<>()).add(post);
        return post;
    }

    public List<Post> getPostsByUser(String userId) {
        return postsByUser.getOrDefault(userId, new ArrayList<>());
    }
}
