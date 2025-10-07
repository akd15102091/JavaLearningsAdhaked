package DesignSocialMediaFeed;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

@SuppressWarnings("unused")
public class Post {
    private String postId;
    private User author;
    private String content;
    private List<String> imageUrls;
    // private List<Like> likes;
    // private List<Comment> comments;
    private long createdAt;
    
    public Post(User author, String content, List<String> imageUrls){
        this.postId = UUID.randomUUID().toString();
        this.author = author;
        this.content = content;
        // this.likes = new CopyOnWriteArrayList<>();
        // this.comments = new CopyOnWriteArrayList<>();
        this.createdAt = System.currentTimeMillis();
    }

    public User getAuthor(){
        return this.author;
    }

    public long getCreatedAt(){
        return this.createdAt;
    }

    public String getContent(){
        return this.content;
    }
}
