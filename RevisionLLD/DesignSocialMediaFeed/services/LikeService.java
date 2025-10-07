package DesignSocialMediaFeed.services;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import DesignSocialMediaFeed.Like;
import DesignSocialMediaFeed.Post;
import DesignSocialMediaFeed.User;

public class LikeService {
    private Map<Post, Map<User, Like>> likesMap = new ConcurrentHashMap<>();

    public void addLikeToPost(Post post, User likedBy){
        Map<User, Like> likes = this.likesMap.getOrDefault(post, new ConcurrentHashMap<>());
        if (likes.containsKey(likedBy)) {
            throw new IllegalStateException("User already liked this post");
        }
        Like like = new Like(likedBy);
        likes.put(likedBy, like);
        this.likesMap.put(post, likes);
    }

    public void removeLikeFromPost(Post post, User likedBy){
        Map<User, Like> likes = this.likesMap.getOrDefault(post, new ConcurrentHashMap<>());
        likes.remove(likedBy);
    }

    public int getLikesCount(Post post){
        return this.likesMap.getOrDefault(post, new ConcurrentHashMap<>()).size();
    }
}
