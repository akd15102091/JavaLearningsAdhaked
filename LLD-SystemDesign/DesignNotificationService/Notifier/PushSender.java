package DesignNotificationService.Notifier;

import java.util.Random;

import DesignNotificationService.Notification;
import DesignNotificationService.User;
import DesignNotificationService.Exceptions.DeliveryFailedException;

public class PushSender implements NotificationSender{
    public void send(User user, Notification notification) throws DeliveryFailedException {
        if (new Random().nextInt(3) == 0) { // Simulate 33% failure
            throw new DeliveryFailedException("Push service failed for user " + user.getName());
        }
        System.out.println("Push notification sent to " + user.getName() + ": " + notification.getMessage());
    }
}
