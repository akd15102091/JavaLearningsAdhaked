package Revision;

import java.util.concurrent.TimeUnit;

public class CustomScheduler {
    // Method to execute a task once after a delay
    public void schedule(Runnable task, long delay, TimeUnit unit){
        new Thread(()->{
            try {
                Thread.sleep(unit.toMillis(delay));
                task.run();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

        }).start();
    }

    // Method to execute a task at a fixed rate
    public void scheduleAtFixedRate(Runnable task, long initialDelay, long period, TimeUnit unit){
        new Thread(()->{
            try {
                Thread.sleep(unit.toMillis(initialDelay));
                while (true) {
                    long startTime = System.currentTimeMillis() ;
                    task.run();
                    long executionTime = System.currentTimeMillis() - startTime;
                    long sleepTime = unit.toMillis(period) - executionTime;
                    if (sleepTime > 0) {
                        Thread.sleep(sleepTime);
                    }
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();
    }

    // Method to execute a task with a fixed delay between executions
    public void scheduleWithFixedDelay(Runnable task, long initialDelay, long delay, TimeUnit unit) {
        new Thread(() -> {
            try {
                Thread.sleep(unit.toMillis(initialDelay)); // Initial delay
                while (true) {
                    task.run();
                    Thread.sleep(unit.toMillis(delay)); // Delay after execution
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();
    }

    public static void main(String[] args) {
        CustomScheduler scheduler = new CustomScheduler();

        // Task to execute once after a delay
        scheduler.schedule(() -> System.out.println("Task 1 executed at: " + System.currentTimeMillis()), 2, TimeUnit.SECONDS);

        // Task to execute at a fixed rate
        scheduler.scheduleAtFixedRate(() -> System.out.println("Task 2 executed at: " + System.currentTimeMillis()), 1, 3, TimeUnit.SECONDS);

        // Task to execute with a fixed delay
        scheduler.scheduleWithFixedDelay(() -> {
            System.out.println("Task 3 executed at: " + System.currentTimeMillis());
            try {
                Thread.sleep(1500); // Simulate task execution time
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, 1, 2, TimeUnit.SECONDS);
    }
}
