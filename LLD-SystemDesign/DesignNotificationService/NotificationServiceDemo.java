package DesignNotificationService;

import java.util.EnumSet;

import DesignNotificationService.AsyncPipeline.AsyncNotificationManager;

public class NotificationServiceDemo {
    public static void main(String[] args) {
        // Service with simple retry (3 attempts, 600ms delay)
        NotificationService service = new NotificationService(3, 600);

        // Async manager with 5 workers
        AsyncNotificationManager async = new AsyncNotificationManager(service, 5);

        // Users & prefs
        User alice = new User("1", "Alice",
                new UserPreferences(EnumSet.of(NotificationChannel.EMAIL, NotificationChannel.PUSH)));
        User bob = new User("2", "Bob",
                new UserPreferences(EnumSet.of(NotificationChannel.SMS)));

        // Submit notifications (user + notification)
        async.submitNotification(alice, new Notification("Welcome, Alice!", NotificationChannel.EMAIL));
        async.submitNotification(alice, new Notification("You have a new message.", NotificationChannel.PUSH));
        async.submitNotification(alice, new Notification("This will be skipped (SMS not enabled).", NotificationChannel.SMS));

        async.submitNotification(bob, new Notification("Your OTP is 123456", NotificationChannel.SMS));
        async.submitNotification(bob, new Notification("Promo for Bob", NotificationChannel.EMAIL)); // skipped

        // Let workers process for a moment
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        async.shutdownNow();
    }
}
