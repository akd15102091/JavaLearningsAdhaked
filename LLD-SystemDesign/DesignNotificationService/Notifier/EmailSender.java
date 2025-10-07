package DesignNotificationService.Notifier;

import java.util.Random;

import DesignNotificationService.Notification;
import DesignNotificationService.User;
import DesignNotificationService.Exceptions.DeliveryFailedException;

public class EmailSender implements NotificationSender{

    @Override
    public void send(User user, Notification notification) throws DeliveryFailedException {
        if(new Random().nextInt(5) == 0){ // simulate 20% failure
            throw new DeliveryFailedException("Email service failed for user " + user.getName());
        }
        System.out.println("Email sent to " + user.getName() + ": " + notification.getMessage());
    }
    
}
