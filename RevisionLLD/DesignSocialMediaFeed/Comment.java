package DesignSocialMediaFeed;

import java.util.UUID;

@SuppressWarnings("unused")
public class Comment {
    private String commentId;
    private User commentedBy;
    private long commentedAt;
    private String comment;
    
    public Comment(User user, String comment){
        this.commentId = UUID.randomUUID().toString();
        this.commentedBy = user;
        this.commentedAt = System.currentTimeMillis();
        this.comment = comment;
    }

    public String getCommentId(){
        return this.commentId;
    }

    public String getCommentText(){
        return this.comment;
    }

    public String getCommentBy(){
        return this.commentedBy.getName();
    }

    @Override
    public String toString(){
        return commentedBy+" has commented on your post: "+comment;
    }
}
