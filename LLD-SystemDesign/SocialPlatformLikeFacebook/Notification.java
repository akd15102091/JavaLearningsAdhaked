package SocialPlatformLikeFacebook;

import java.time.LocalDateTime;
import java.util.UUID;

public class Notification {
    String notificationId;
    String userId;
    String message;
    LocalDateTime timestamp;

    public Notification(String userId, String message) {
        this.notificationId = UUID.randomUUID().toString();
        this.userId = userId;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }
}
