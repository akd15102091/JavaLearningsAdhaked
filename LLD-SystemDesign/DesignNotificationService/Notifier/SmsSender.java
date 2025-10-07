package DesignNotificationService.Notifier;

import java.util.Random;

import DesignNotificationService.Notification;
import DesignNotificationService.User;
import DesignNotificationService.Exceptions.DeliveryFailedException;

public class SmsSender implements NotificationSender{
    public void send(User user, Notification notification) throws DeliveryFailedException {
        if (new Random().nextInt(4) == 0) { // Simulate 25% failure
            throw new DeliveryFailedException("SMS service failed for user " + user.getName());
        }
        System.out.println("SMS sent to " + user.getName() + ": " + notification.getMessage());
    }
}
