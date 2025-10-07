package Revision_Multithreading.ThreadPool;

import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

class Task implements Runnable{
    int taskId;
    public Task(int id){
        this.taskId = id;
    }
    @Override
    public void run() {
        System.out.println("Taskid: "+taskId+" by thread : "+Thread.currentThread().getId());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
}

@SuppressWarnings("unused")
public class CustomThreadPoolExecuter {
    private final BlockingQueue<Runnable> taskQueue;
    private final int corePoolSize;
    private WorkerThread[] workers ;
    
    
    public CustomThreadPoolExecuter(int corePoolSize, int queueCapacity) {
        this.corePoolSize = corePoolSize;
        this.workers = new WorkerThread[corePoolSize];
        this.taskQueue = new ArrayBlockingQueue<>(queueCapacity) ;

        for(int i=0; i<corePoolSize; i++){
            WorkerThread thread = new WorkerThread();
            this.workers[i] = thread;
            thread.start();
        }
    }

    class WorkerThread extends Thread{
        public WorkerThread(){
            String tname = UUID.randomUUID().toString();
            this.setName(tname);
        }
    
        public void run(){
            while (true) {
                try {
                    Runnable task = taskQueue.take();
                    task.run();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    public void submit(Runnable task){
        try {
            taskQueue.put(task) ;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    

    public static void main(String[] args) {
        CustomThreadPoolExecuter customThreadPoolExecuter = new CustomThreadPoolExecuter(3, 5) ;

        for(int i=0; i<15; i++){
            Task newTask = new Task(i+1) ;
            customThreadPoolExecuter.submit(newTask);
        }

        try {
            Thread.sleep(25000); // Allow time for tasks to execute before shutdown
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
