package Revision_Multithreading;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import Revision.CustomThreadPoolExecutor;

@SuppressWarnings("unused")
public class CustomThreadPoolExecuter {
    private final int corePoolSize;
    private final BlockingQueue<Runnable> taskQueue;
    private final WorkerThread[] workers;
    private volatile boolean isShutdown = false;

    public CustomThreadPoolExecuter(int corePoolSize) {
        this.corePoolSize = corePoolSize;
        this.taskQueue = new ArrayBlockingQueue<>(5) ;
        this.workers = new WorkerThread[corePoolSize] ;
        
        for(int i=0; i<corePoolSize; i++){
            WorkerThread workerThread = new WorkerThread() ;
            this.workers[i] = workerThread;
            workerThread.start();
        }
    }

    static class Task implements Runnable{
        int taskId;
        public Task(int id){
            this.taskId = id;
        }

        @Override
        public void run() {
            System.out.println("Task id "+ taskId +" running by thread "+ Thread.currentThread().getId());
            try {
                long l =  (long) (Math.random()*1000);
                Thread.sleep(1000+ l);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
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
        this.isShutdown = true;
        for(WorkerThread thread : workers){
            thread.interrupt();
        }
    }

    class WorkerThread extends Thread{
        public void run(){
            while (true) {
                Runnable task;
                try {
                    task = taskQueue.take() ;
                    task.run();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        CustomThreadPoolExecutor executor = new CustomThreadPoolExecutor(3) ;
        for(int i=1; i<=15;i++){
            int taskId = i;
            Task task = new Task(taskId);

            executor.submit(task);
        }

        try {
            Thread.sleep(20000); // Allow time for tasks to execute before shutdown
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        executor.shutdown();
    }

}
