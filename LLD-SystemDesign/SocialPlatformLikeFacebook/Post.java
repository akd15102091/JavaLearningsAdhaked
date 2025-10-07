package SocialPlatformLikeFacebook;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class Post {
    public String postId;
    public String authorId;
    public String content;
    public List<String> mediaUrls;
    public LocalDateTime timestamp;
    public Set<String> likeUserIds;
    public List<Comment> comments;

    public Post(String authorId, String content, List<String> mediaUrls) {
        this.postId = UUID.randomUUID().toString();
        this.authorId = authorId;
        this.content = content;
        this.mediaUrls = mediaUrls;
        this.timestamp = LocalDateTime.now();
        this.likeUserIds = new HashSet<>();
        this.comments = new ArrayList<>();
    }
}
