package DesignNotificationService.Exceptions;

public class DeliveryFailedException extends NotificationException{
    public DeliveryFailedException(String message) {
        super(message);
    }
}
