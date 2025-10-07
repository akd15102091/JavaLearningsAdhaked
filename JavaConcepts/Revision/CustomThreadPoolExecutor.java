package Revision;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@SuppressWarnings("unused")
public class CustomThreadPoolExecutor {
    private final int poolSize;
    private final WorkerThread[] workers;
    private final BlockingQueue<Runnable> taskQueue;
    private volatile boolean isShutdown = false;

    public CustomThreadPoolExecutor(int poolSize){
        this.poolSize = poolSize;
        this.taskQueue = new LinkedBlockingQueue<>();
        this.workers = new WorkerThread[poolSize];

        for (int i = 0; i < poolSize; i++) {
            workers[i] = new WorkerThread();
            workers[i].start();
        }
    }

    public void submit(Runnable task){
        if(!isShutdown){
            taskQueue.offer(task) ;
        }
        else{
            throw new IllegalStateException("ThreadPool is shutting down, cannot accept new tasks.");
        }
    }

    public void shutdown(){
        isShutdown = true;
        for(WorkerThread thread: workers){
            thread.interrupt(); // interrupting thread as we are shutting down the executer
        }
    }

    private class WorkerThread extends Thread{
        public void run(){
            while (true) {
                Runnable task;
                try {
                    task = taskQueue.take();
                    task.run();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        CustomThreadPoolExecutor executor = new CustomThreadPoolExecutor(2) ;
        for(int i=1; i<=10;i++){
            int taskId = i;

            executor.submit(()-> {
                System.out.println("Task " + taskId + " executed by " + Thread.currentThread().getName());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }

        try {
            Thread.sleep(10000); // Allow time for tasks to execute before shutdown
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        executor.shutdown();
    }
}
