package DesignStackOverflow;

import java.util.UUID;

@SuppressWarnings({"unused"})
public class Comment {
    private String id;
    private String commentDescription;
    private User commenter;
    private Long timestamp;

    public Comment(String commentDescription, User commenter) {
        this.commentDescription = commentDescription;
        this.commenter = commenter;

        this.id = UUID.randomUUID().toString();
        this.timestamp = System.currentTimeMillis();
    }

    public String getId(){
        return id;
    }

}
