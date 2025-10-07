package DesignJobSchedular;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WorkerPool {
    private ExecutorService pool ;

    public WorkerPool(int numberOfWorkers){
        this.pool = Executors.newFixedThreadPool(numberOfWorkers);
    }

    public void submit(Runnable task){
        this.pool.submit(task);
    }
}
