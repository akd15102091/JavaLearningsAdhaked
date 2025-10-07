package Threads;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class SharedQueue {
    private final Queue<Integer> queue = new LinkedList<>();
    private final int capacity;
    private final Lock lock = new ReentrantLock();
    private final Condition notFull = lock.newCondition();
    private final Condition notEmpty = lock.newCondition();

    public SharedQueue(int capacity) {
        this.capacity = capacity;
    }

    public void produce() {
        int value = 1;
        while (true) {
            lock.lock();
            try {
                while (queue.size() == capacity) {
                    System.out.println("Queue is full. Producer is waiting...");
                    notFull.await();  // Wait if queue is full
                }
                queue.offer(value);
                System.out.println("Produced: " + value);
                value++;
                notEmpty.signal();  // Notify consumer
                Thread.sleep(500);  // Simulating work
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            } finally {
                lock.unlock();
            }
        }
    }

    public void consume() {
        while (true) {
            lock.lock();
            try {
                while (queue.isEmpty()) {
                    System.out.println("Queue is empty. Consumer is waiting...");
                    notEmpty.await();  // Wait if queue is empty
                }
                int value = queue.poll();
                System.out.println("Consumed: " + value);
                notFull.signal();  // Notify producer
                Thread.sleep(1000);  // Simulating work
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            } finally {
                lock.unlock();
            }
        }
    }
}

class Producer implements Runnable {
    private final SharedQueue queue;

    public Producer(SharedQueue queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        queue.produce();
    }
}

class Consumer implements Runnable {
    private final SharedQueue queue;

    public Consumer(SharedQueue queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        queue.consume();
    }
}

public class ProducerConsumerLockExample {
    public static void main(String[] args) {
        SharedQueue queue = new SharedQueue(5);

        Thread producerThread = new Thread(new Producer(queue));
        Thread consumerThread = new Thread(new Consumer(queue));

        producerThread.start();
        consumerThread.start();
    }
}
