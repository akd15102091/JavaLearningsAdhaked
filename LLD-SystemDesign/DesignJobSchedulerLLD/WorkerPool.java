package DesignJobSchedulerLLD;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WorkerPool {
    private final ExecutorService executor = Executors.newFixedThreadPool(4);

    public void submit(Runnable runnable) {
        executor.submit(runnable);
    }

    public void shutdown() {
        executor.shutdown();
    }
}
