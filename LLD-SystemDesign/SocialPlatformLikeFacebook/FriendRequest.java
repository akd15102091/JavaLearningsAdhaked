package SocialPlatformLikeFacebook;

import java.util.UUID;

public class FriendRequest {
    public String requestId;
    public String fromUserId;
    public String toUserId;
    public boolean isAccepted;

    public FriendRequest(String from, String to) {
        this.requestId = UUID.randomUUID().toString();
        this.fromUserId = from;
        this.toUserId = to;
    }
}
