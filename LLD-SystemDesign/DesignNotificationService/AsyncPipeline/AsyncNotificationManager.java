package DesignNotificationService.AsyncPipeline;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import DesignNotificationService.Notification;
import DesignNotificationService.NotificationService;
import DesignNotificationService.User;

public class AsyncNotificationManager {
    private final NotificationQueue queue = new NotificationQueue();
    private final ExecutorService pool;
    private final AtomicInteger idGen = new AtomicInteger(1);

    public AsyncNotificationManager(NotificationService service, int threadCount) {
        pool = Executors.newFixedThreadPool(threadCount, r -> {
            Thread t = new Thread(r, "notif-worker-" + idGen.getAndIncrement());
            t.setDaemon(true);
            return t;
        });
        for (int i = 0; i < threadCount; i++) {
            pool.submit(new NotificationWorker(i + 1, queue, service));
        }
    }

    public void submitNotification(User user, Notification notification) {
        queue.enqueue(new NotificationTask(user, notification));
    }

    public int queued() { return queue.size(); }

    public void shutdownNow() {
        pool.shutdownNow();
    }
}
