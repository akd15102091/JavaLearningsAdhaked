package DesignNotificationService.AsyncPipeline;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class NotificationQueue {
    private final BlockingQueue<NotificationTask> queue = new LinkedBlockingQueue<>();

    public void enqueue(NotificationTask task) { queue.offer(task); }

    public NotificationTask dequeue() throws InterruptedException { return queue.take(); }

    public int size() { return queue.size(); }
}
