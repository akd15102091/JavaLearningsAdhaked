package DesignNotificationService.AsyncPipeline;

import DesignNotificationService.NotificationService;
import DesignNotificationService.Exceptions.ChannelNotEnabledException;
import DesignNotificationService.Exceptions.NotificationException;

public class NotificationWorker implements Runnable{
    private final NotificationQueue queue;
    private final NotificationService service;
    private final int workerId;

    NotificationWorker(int id, NotificationQueue queue, NotificationService service) {
        this.workerId = id; this.queue = queue; this.service = service;
    }

    @Override public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                NotificationTask task = queue.dequeue();
                service.sendNotification(task.user, task.notification);
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt(); // graceful shutdown
            } catch (ChannelNotEnabledException ce) {
                // business skip: not an error
                System.out.println("[WORKER " + workerId + "] Skipped: " + ce.getMessage());
            } catch (NotificationException ne) {
                System.err.println("[WORKER " + workerId + "] Permanent failure: " + ne.getMessage());
            } catch (Exception e) {
                System.err.println("[WORKER " + workerId + "] Unexpected: " + e);
            }
        }
    }
}
