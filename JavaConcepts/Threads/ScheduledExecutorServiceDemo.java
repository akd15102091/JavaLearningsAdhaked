package Threads;

import java.util.concurrent.*;

public class ScheduledExecutorServiceDemo {

    public ScheduledExecutorServiceDemo() {
    }

    /**
     * Creates and executes a one-shot action that becomes enabled after the given delay.
     */
    public void schedule(Runnable command, long delay, TimeUnit unit) {
        new Thread(() -> {
            try {
                Thread.sleep(unit.toMillis(delay));
                command.run();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();
    }

    /**
     * Creates and executes a periodic action that becomes enabled first after the given initial delay, and 
     * subsequently with the given period.
     */
    public void scheduleAtFixedRate(Runnable command, long initialDelay, long period, TimeUnit unit) {
        new Thread(() -> {
            try {
                Thread.sleep(unit.toMillis(initialDelay));
                while (!Thread.currentThread().isInterrupted()) {
                    long start = System.currentTimeMillis();
                    command.run();
                    long elapsed = System.currentTimeMillis() - start;
                    Thread.sleep(Math.max(0, unit.toMillis(period) - elapsed));
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();
    }

    /**
     * Creates and executes a periodic action that becomes enabled first after the given initial delay, and 
     * subsequently with the given delay between the termination of one execution and the commencement of the next.
     */
    public void scheduleWithFixedDelay(Runnable command, long initialDelay, long delay, TimeUnit unit) {
        new Thread(() -> {
            try {
                Thread.sleep(unit.toMillis(initialDelay));
                while (!Thread.currentThread().isInterrupted()) {
                    command.run();
                    Thread.sleep(unit.toMillis(delay));
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();
    }

    public static void main(String[] args) {
        ScheduledExecutorServiceDemo scheduler = new ScheduledExecutorServiceDemo();

        Runnable task1 = () -> System.out.println("One-time task executed at: " + System.currentTimeMillis());
        scheduler.schedule(task1, 2, TimeUnit.SECONDS);

        Runnable task2 = () -> System.out.println("Fixed rate task executed at: " + System.currentTimeMillis());
        scheduler.scheduleAtFixedRate(task2, 1, 3, TimeUnit.SECONDS);

        Runnable task3 = () -> System.out.println("Fixed delay task executed at: " + System.currentTimeMillis());
        scheduler.scheduleWithFixedDelay(task3, 1, 4, TimeUnit.SECONDS);
    }
}

