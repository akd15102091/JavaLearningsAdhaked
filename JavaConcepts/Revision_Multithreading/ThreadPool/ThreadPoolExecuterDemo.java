package Revision_Multithreading.ThreadPool;

import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

class CustomThreadFactory implements ThreadFactory{

    @Override
    public Thread newThread(Runnable r) {
        String name = UUID.randomUUID().toString();
        Thread t = new Thread(r);
        t.setName(name);
        return t;
    }
    
}

class CustomRejectHandler implements RejectedExecutionHandler{

    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        System.out.println("Runnable task: "+r.getClass().getName()+" is rejected by executer : "+executor.getClass());
    }
    
}

public class ThreadPoolExecuterDemo {
    public static void main(String[] args) {
        ThreadPoolExecutor executer = new ThreadPoolExecutor(3, 5, 1, TimeUnit.SECONDS,
         new ArrayBlockingQueue<>(5), new CustomThreadFactory(), new CustomRejectHandler());

        for(int i=0; i<15; i++){
            int id = i+1;
            executer.submit(()->{
                System.out.println("Taskid: "+id+" by thread : "+Thread.currentThread().getName());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }) ;
        }

        // Note: Here 5 task will be assigned to 5 total threads, 5 will be in queue and 
        // other 5 will be rejected by pool executer.
    }
}
