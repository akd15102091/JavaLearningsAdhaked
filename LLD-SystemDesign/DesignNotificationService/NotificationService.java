package DesignNotificationService;

import java.util.HashMap;
import java.util.Map;

import DesignNotificationService.Exceptions.ChannelNotEnabledException;
import DesignNotificationService.Exceptions.DeliveryFailedException;
import DesignNotificationService.Exceptions.NotificationException;
import DesignNotificationService.Notifier.EmailSender;
import DesignNotificationService.Notifier.NotificationSender;
import DesignNotificationService.Notifier.PushSender;
import DesignNotificationService.Notifier.SmsSender;

public class NotificationService {
    private Map<NotificationChannel, NotificationSender> senderMap = new HashMap<>();
    private final int maxRetries;
    private final long retryDelayMs;

    public NotificationService(int maxRetries, long retryDelayMs) {
        this.maxRetries = maxRetries;
        this.retryDelayMs = retryDelayMs;
        senderMap.put(NotificationChannel.EMAIL, new EmailSender());
        senderMap.put(NotificationChannel.SMS, new SmsSender());
        senderMap.put(NotificationChannel.PUSH, new PushSender());
    }

    public void sendNotification(User user, Notification notification) throws NotificationException {
        if (!user.getPreferences().isChannelEnabled(notification.getChannel())) {
            throw new ChannelNotEnabledException("Channel not enabled for user: " + user.getName());
        }

        NotificationSender sender = senderMap.get(notification.getChannel());
        int attempts = 0;

        while (attempts < maxRetries) {
            try {
                sender.send(user, notification);
                return; // success
            } catch (DeliveryFailedException e) {
                attempts++;
                System.err.println("Attempt " + attempts + " failed: " + e.getMessage());
                if (attempts >= maxRetries) {
                    throw e; // rethrow after max retries
                }
                try {
                    Thread.sleep(retryDelayMs); // wait before retry
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    throw new NotificationException("Retry interrupted");
                }
            }
        }

    }
}
