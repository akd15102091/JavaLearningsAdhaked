package Revision.InterviewProblems;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Implement two methods in a class, schedule() and waitUntilComplete()
 * schedule() - should enqueue work to be performed and should be non-blocking.
 * waitUntilComplete() - should block the call until all scheduled work is completed.
 * 
 * Builtin thead safe constructs like Deque, BlockingQueue etc can't be used. 
 * Implement it using locks, etc ensuring thread safety.
 */

public class Problem01 {
    Queue<Runnable> queue = new LinkedList<>();
    ReentrantLock lock = new ReentrantLock();
    Condition allCompleted = lock.newCondition();
    int activeTasks = 0;

    public Problem01(){
    }

    public void schedule(Task task){
        lock.lock();
        try {
            queue.offer(()->{
                try {
                    task.run();
                } finally {
                    taskCompleted();
                }
            });
            activeTasks++;
            new Thread(this::executeTask).start();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void executeTask(){
        lock.lock();
        Runnable task;
        try {
            task = queue.poll();
            if(task!=null){
                task.run();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void taskCompleted(){
        lock.lock();
        try {
            activeTasks--;
            if(activeTasks==0){
                allCompleted.signalAll();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void waitUntilComplete(){
        lock.lock();
        try {
            while (activeTasks>0) {
                allCompleted.await();
            }
            System.out.println("All tasks are completed. Running waitUntilComplete function...");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    static class Task implements Runnable{
        int taskId;
        public Task(int id){
            this.taskId = id ;
        }

        @Override
        public void run() {
            try {
                long time = (long) (Math.random()*1000);
                Thread.sleep(time);
                System.out.println("Runnable task with id: "+ taskId +" is running by thread "+ Thread.currentThread().getName() +" .......");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
    }

    public static void main(String[] args) {
        Problem01 driver = new Problem01();

        for(int i=1; i<=5; i++){
            Task task = new Task(i);
            driver.schedule(task);
        }

        driver.waitUntilComplete();
    }


}
