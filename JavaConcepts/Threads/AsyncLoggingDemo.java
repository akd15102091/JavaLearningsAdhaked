package Threads;


/*
 * 
 * Asynchronous Logging in Multithreading (Java)
        In a multithreaded Java application, asynchronous logging helps improve performance by offloading log-writing operations to a separate thread, preventing logging from blocking the main execution flow.

    Approach:
        Use a blocking queue to store log messages.
        A separate logging thread will take messages from the queue and write them to a log file.
        Ensure thread safety using ConcurrentLinkedQueue or BlockingQueue.
 */

import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

class AsyncLogger {
    private static final BlockingQueue<String> logQueue = new LinkedBlockingQueue<>();
    private static final String LOG_FILE = "app.log";
    private static volatile boolean running = true;

    // Start a separate thread for logging
    static {
        Thread loggerThread = new Thread(() -> {
            try (FileWriter writer = new FileWriter(LOG_FILE, true)) {
                while (running || !logQueue.isEmpty()) {
                    String logMessage = logQueue.poll();
                    if (logMessage != null) {
                        writer.write(logMessage + "\n");
                        writer.flush();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        loggerThread.setDaemon(true);  // Runs in the background
        loggerThread.start();
    }

    public static void log(String message) {
        logQueue.offer(Thread.currentThread().getName() + " - " + message);
    }

    public static void stop() {
        running = false;
    }
}

public class AsyncLoggingDemo {
    public static void main(String[] args) throws InterruptedException {
        Runnable task = () -> {
            for (int i = 0; i < 5; i++) {
                AsyncLogger.log("Message " + i);
                try {
                    Thread.sleep(100);  // Simulate work
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        };

        Thread t1 = new Thread(task, "Thread-1");
        Thread t2 = new Thread(task, "Thread-2");

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        AsyncLogger.stop();  // Stop the logging thread gracefully

        System.out.println("Logging completed.");
    }
}

