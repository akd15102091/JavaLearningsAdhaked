package Revision;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolExecuterDemo {
    public static void main(String[] args) {
        Runnable r1 = new Task("task 1"); 
        Runnable r2 = new Task("task 2"); 
        Runnable r3 = new Task("task 3"); 
        Runnable r4 = new Task("task 4"); 
        Runnable r5 = new Task("task 5");       

        ThreadPoolExecutor executorPool = new ThreadPoolExecutor(
            2, 4, 1, TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(5 ),
            new CustomFactory()

        ) ;

        executorPool.execute(r1); 
        executorPool.execute(r2); 
        executorPool.execute(r3); 
        executorPool.execute(r4); 
        executorPool.execute(r5); 
    }
}

class CustomFactory implements ThreadFactory{

    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(r) ;
        t.setName(UUID.randomUUID().toString());
        return t;
    }
    
}

class Task implements Runnable    
{ 
    private String name; 
      
    public Task(String s) 
    { 
        name = s; 
    } 
      
    // Prints task name and sleeps for 1s 
    // This Whole process is repeated 5 times 
    public void run() 
    { 
        try
        { 
            for (int i = 0; i<=5; i++) 
            { 
                if (i==0) 
                { 
                    Date d = new Date(); 
                    SimpleDateFormat ft = new SimpleDateFormat("hh:mm:ss"); 
                    System.out.println("Initialization Time for"
                            + " task name - "+ name +" = " +ft.format(d) + ", id:"+Thread.currentThread().getName());    
                    //prints the initialization time for every task  
                } 
                else
                { 
                    Date d = new Date(); 
                    SimpleDateFormat ft = new SimpleDateFormat("hh:mm:ss"); 
                    System.out.println("Executing Time for task name - "+ 
                            name +" = " +ft.format(d) + ", id:"+Thread.currentThread().getName());    
                    // prints the execution time for every task  
                } 
                Thread.sleep(1000); 
            } 
            System.out.println(name+" complete"); 
        } 
          
        catch(InterruptedException e) 
        { 
            e.printStackTrace(); 
        } 
    } 
} 