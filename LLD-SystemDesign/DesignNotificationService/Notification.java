package DesignNotificationService;

public class Notification {
    private final String message;
    private final NotificationChannel channel;

    public Notification(String message, NotificationChannel channel) {
        this.message = message;
        this.channel = channel;
    }

    public String getMessage() { return message; }
    public NotificationChannel getChannel() { return channel; }
}
