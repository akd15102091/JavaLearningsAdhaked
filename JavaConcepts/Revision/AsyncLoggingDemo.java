package Revision;

import java.io.FileWriter;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/*
 * 
 * Asynchronous Logging in Multithreading (Java)
        In a multithreaded Java application, asynchronous logging helps improve performance by offloading log-writing operations to a separate thread, preventing logging from blocking the main execution flow.

    Approach:
        Use a blocking queue to store log messages.
        A separate logging thread will take messages from the queue and write them to a log file.
        Ensure thread safety using ConcurrentLinkedQueue or BlockingQueue.
 */

class AsyncLogger{
    private static final BlockingQueue<String> logQueue = new LinkedBlockingQueue<>();
    private static final String LOG_FILE = "app.log";
    private static volatile boolean running = true;

    static {
        Thread loggerThread = new Thread(()->{
            try (FileWriter fileWriter = new FileWriter(LOG_FILE, true)){
                while (running) {
                    String logMessage = logQueue.take();
                    if(logMessage != null){
                        fileWriter.write(logMessage +"\n"); 
                        fileWriter.flush();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        loggerThread.setDaemon(true); // runs in background
        loggerThread.start();
    }

    public static void log(String message){
        logQueue.offer(Thread.currentThread().getName() + " " + message) ;
    }

}

public class AsyncLoggingDemo {
    public static void main(String[] args) {
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

        Thread t1 = new Thread(task, "Thread1");
        Thread t2 = new Thread(task, "Thread2");

        t1.start();
        t2.start();
    }

}
