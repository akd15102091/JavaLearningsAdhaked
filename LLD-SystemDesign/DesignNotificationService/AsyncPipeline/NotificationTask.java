package DesignNotificationService.AsyncPipeline;

import DesignNotificationService.Notification;
import DesignNotificationService.User;

public class NotificationTask {
    final User user;
    final Notification notification;
    NotificationTask(User user, Notification notification) { this.user = user; this.notification = notification; }
}
