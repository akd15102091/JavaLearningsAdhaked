package Revision_Multithreading.InterviewProblems;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * /**
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

    public void schedule(Runnable Task){
        lock.lock();
        try {
            queue.add(Task);
            activeTasks++;
            new Thread(this::performTask).start();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void performTask(){
        lock.lock();
        try {
            Runnable task = queue.poll();
            if(task!=null){
                task.run();
                activeTasks--;
                if(activeTasks==0){
                    allCompleted.signalAll();
                }
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
                try {
                    allCompleted.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("\nAll tasks are completed !");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }


    }

    static class Task implements Runnable{
        private int taskId;
        public Task(int taskId){
            this.taskId = taskId;
        }

        @Override
        public void run(){
            try {
                System.out.println("Task-"+taskId+" is running by thread-"+Thread.currentThread().getName()+" ....");
                long sleepTime = (long) (Math.random()*1000);
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Problem01 driver = new Problem01();

        for(int i=1; i<=10; i++){
            Task task = new Task(i);
            driver.schedule(task);
        }

        driver.waitUntilComplete();
    }
}
