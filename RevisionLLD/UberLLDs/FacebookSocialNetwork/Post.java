package UberLLDs.FacebookSocialNetwork;

public class Post {
    private final long id;
    private final long authorId;
    private final String content;
    private final long createdAt;

    Post(long id, long authorId, String content, long createdAt) {
        this.id = id;
        this.authorId = authorId;
        this.content = content;
        this.createdAt = createdAt;
    }

    public long getId() { return id; }
    public long getAuthorId() { return authorId; }
    String getContent() { return content; }
    long getCreatedAt() { return createdAt; }
}
