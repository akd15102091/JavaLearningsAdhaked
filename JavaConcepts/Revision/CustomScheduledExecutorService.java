package Revision;

// import java.util.ArrayList;
// import java.util.List;
// import java.util.PriorityQueue;
// import java.util.concurrent.ExecutorService;
// import java.util.concurrent.Executors;
// import java.util.concurrent.ScheduledExecutorService;
// import java.util.concurrent.ScheduledThreadPoolExecutor;
// import java.util.concurrent.TimeUnit;

public class CustomScheduledExecutorService {
    // private final int corePoolSize;
    // private final PriorityQueue taskQueue = new PriorityQueue<>();
    // private final List<Thread> workers;
    // private volatile boolean running = true;
    // private final Object lock = new Object();

    // public CustomScheduledExecutorService(int corePoolSize) {
    //     this.corePoolSize = corePoolSize ;
    //     workers = new ArrayList<>();
    //     for(int i=0; i<corePoolSize; i++){
    //         Thread worker = new Thread();
    //         worker.start();
    //         this.workers.add(worker) ;
    //     }
    // }

    // private void processTasks() {
    //     while (running) {
    //         synchronized (lock) {
    //             while (taskQueue.isEmpty() || taskQueue.peek().executionTime > System.currentTimeMillis()) {
    //                 try {
    //                     if (!taskQueue.isEmpty()) {
    //                         lock.wait(taskQueue.peek().executionTime - System.currentTimeMillis());
    //                     } else {
    //                         lock.wait();
    //                     }
    //                 } catch (InterruptedException ignored) {}
    //             }
    //             ScheduledTask task = taskQueue.poll();
    //             if (task != null) {
    //                 new Thread(task.command).start();
    //                 if (task.isRepeating) {
    //                     task.executionTime = task.fixedRate ? task.executionTime + task.period : System.currentTimeMillis() + task.period;
    //                     taskQueue.offer(task);
    //                 }
    //             }
    //         }
    //     }
    // }

    // /**
    //  * Creates and executes a one-shot action that becomes enabled after the given delay.
    //  */
    // public void schedule(Runnable command, long delay, TimeUnit unit) {
    // }

    // /**
    //  * Creates and executes a periodic action that becomes enabled first after the given initial delay,
    //  * and subsequently with the given period.
    //  */
    // public void scheduleAtFixedRate(Runnable command, long initialDelay, long period, TimeUnit unit) {
    // }

    // /**
    //  * Creates and executes a periodic action that becomes enabled first after the given initial delay,
    //  * and subsequently with the given delay between the termination of one execution and the
    //  * commencement of the next.
    //  */
    // public void scheduleWithFixedDelay(Runnable command, long initialDelay, long delay, TimeUnit unit) {
    // }



    // /* Implementation */
    // private static class ScheduledTask{
    //     private Runnable command;
    //     private long executionTime;
    //     private long period;
    //     private boolean isRepeating;
    //     private boolean fixedRate;

    //     public ScheduledTask(Runnable command, long executionTime, long period, boolean isRepeating,
    //             boolean fixedRate) {
    //         this.command = command;
    //         this.executionTime = executionTime;
    //         this.period = period;
    //         this.isRepeating = isRepeating;
    //         this.fixedRate = fixedRate;
    //     }
    // }
  
}
