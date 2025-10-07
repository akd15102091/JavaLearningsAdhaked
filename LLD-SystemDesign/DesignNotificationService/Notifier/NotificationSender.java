package DesignNotificationService.Notifier;

import DesignNotificationService.Notification;
import DesignNotificationService.User;
import DesignNotificationService.Exceptions.DeliveryFailedException;

public interface NotificationSender {
    void send(User user, Notification notification) throws DeliveryFailedException;
}
