package DesignSocialMediaFeed.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import DesignSocialMediaFeed.Post;
import DesignSocialMediaFeed.User;

public class PostService {
    private Map<User, List<Post>> userPosts = new ConcurrentHashMap<>();
    
    public Post createPost(User author, String content, List<String> imageUrls){
        Post post = new Post(author, content, imageUrls);
        this.userPosts.computeIfAbsent(author, k -> Collections.synchronizedList(new ArrayList<>())).add(post);
        return post;
    }

    public List<Post> getPostsByUser(User user){ // return in reverse order, which is basically adding time sorted order
        List<Post> posts = new ArrayList<>(this.userPosts.getOrDefault(user, new ArrayList<>()));
        Collections.reverse(posts);
        return posts;
    }

}
