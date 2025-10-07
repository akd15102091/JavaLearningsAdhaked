package SocialPlatformLikeFacebook;

import java.time.LocalDateTime;
import java.util.UUID;

public class Comment {
    String commentId;
    String userId;
    String content;
    LocalDateTime timestamp;

    public Comment(String userId, String content) {
        this.commentId = UUID.randomUUID().toString();
        this.userId = userId;
        this.content = content;
        this.timestamp = LocalDateTime.now();
    }
}
