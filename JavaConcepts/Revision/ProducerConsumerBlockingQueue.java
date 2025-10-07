package Revision;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

class SharedQueue{
    private Queue<Integer> queue = new LinkedList<>();
    private final int MAX_CAPACITY = 3;
    private ReentrantLock lock = new ReentrantLock();
    Condition canProduce = lock.newCondition() ;
    Condition canConsume = lock.newCondition() ;

    public void produce() throws InterruptedException{
        while(true) {
            lock.lock();
            try {
                while(queue.size()>=MAX_CAPACITY){
                    System.out.println("Producer is waiting as queue is full...");
                    canProduce.await();
                }
                int id = (int) (Math.random()*100) ;
                queue.add(id) ;
                System.out.println("Produced item: "+id);
                canConsume.signalAll();
            } catch (Exception e) {
                e.printStackTrace();
            } finally{
                lock.unlock();
            }
            int waitTime = (int) (Math.random()*1000) ;
            Thread.sleep(waitTime);
        }
    }

    public void consume() throws InterruptedException{
        while(true) {
            lock.lock();
            try {
                while(queue.size()==0){
                    System.out.println("Consumer is waiting as queue is empty...");
                    canConsume.await();
                }
                
                int consumedItem = queue.poll() ;
                System.out.println("Consumed item: "+consumedItem);
                canProduce.signalAll();
            } catch (Exception e) {
                e.printStackTrace();
            } finally{
                lock.unlock();
            }
            int waitTime = (int) (Math.random()*1000) ;
            Thread.sleep(waitTime);
        }
    }
}

class Producer implements Runnable{
    private SharedQueue queue;

    public Producer(SharedQueue queue){
        this.queue = queue;
    }

    @Override
    public void run(){
        try {
            queue.produce();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}

class Consumer implements Runnable{
    private SharedQueue queue;

    public Consumer(SharedQueue queue){
        this.queue = queue;
    }

    @Override
    public void run(){
        try {
            queue.consume();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}

public class ProducerConsumerBlockingQueue {
    public static void main(String[] args) throws InterruptedException {
        SharedQueue sharedQueue = new SharedQueue();

        Producer producer1 = new Producer(sharedQueue) ;
        Consumer consumer1 = new Consumer(sharedQueue) ;

        Thread t1 = new Thread(producer1);
        Thread t2 = new Thread(consumer1) ;

        t1.start();
        t2.start();

        t1.join();
        t2.join();

    }
}
