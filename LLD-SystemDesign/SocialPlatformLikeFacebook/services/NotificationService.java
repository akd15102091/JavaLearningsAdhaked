package SocialPlatformLikeFacebook.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import SocialPlatformLikeFacebook.Notification;

public class NotificationService {
    private static NotificationService instance;
    private Map<String, List<Notification>> userNotifications = new HashMap<>();

    private NotificationService() {}

    public static NotificationService getInstance() {
        if (instance == null) {
            instance = new NotificationService();
        }
        return instance;
    }

    public void notify(String userId, String message) {
        Notification notification = new Notification(userId, message);
        userNotifications.computeIfAbsent(userId, k -> new ArrayList<>()).add(notification);
    }

    public List<Notification> getNotifications(String userId) {
        return userNotifications.getOrDefault(userId, new ArrayList<>());
    }
}
